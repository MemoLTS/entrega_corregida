package com.facturacion.facturacionm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.facturacion.facturacionm.exception.BusinessException;
import com.facturacion.facturacionm.exception.ResourceNotFoundException;
import com.facturacion.facturacionm.feign.ClienteFeignClient;
import com.facturacion.facturacionm.model.DetalleFactura;
import com.facturacion.facturacionm.model.Factura;
import com.facturacion.facturacionm.repository.DetalleFacturaRepository;
import com.facturacion.facturacionm.repository.FacturaRepository;

@Service
public class FacturaService {

    private static final Logger log = LoggerFactory.getLogger(FacturaService.class);

    private final FacturaRepository facturaRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final ClienteFeignClient clienteFeignClient;

    public FacturaService(FacturaRepository facturaRepository,
                          DetalleFacturaRepository detalleFacturaRepository,
                          ClienteFeignClient clienteFeignClient) {
        this.facturaRepository = facturaRepository;
        this.detalleFacturaRepository = detalleFacturaRepository;
        this.clienteFeignClient = clienteFeignClient;
    }

    public List<Factura> listarTodasFactura() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> obtenerPorId(Long idFactura) {
        return facturaRepository.findById(idFactura);
    }

    public List<Factura> listarPorUsuario(Long usuarioRut) {
        return facturaRepository.findByUsuarioRut(usuarioRut);
    }

    public void eliminarFactura(Long idFactura) {
        if (!facturaRepository.existsById(idFactura)) {
            log.warn("Intento de eliminar factura inexistente: id={}", idFactura);
            throw new ResourceNotFoundException("Factura no encontrada: " + idFactura);
        }
        facturaRepository.deleteById(idFactura);
        log.info("Factura eliminada: id={}", idFactura);
    }

    public Factura generarFactura(Long usuarioRut, int pedidoId, List<DetalleFactura> detalles) {
        log.info("Generando factura para usuarioRut={}, pedidoId={}", usuarioRut, pedidoId);
        Map<String, Object> usuario = clienteFeignClient.obtenerUsuarioPorRut(usuarioRut);

        if ("Usuario no disponible".equals(usuario.get("nombre"))) {
            log.error("No se pudo generar la factura: servicio de usuarios no disponible para usuarioRut={}", usuarioRut);
            throw new BusinessException("El microservicio de usuarios no está disponible.");
        }

        if (detalles == null || detalles.isEmpty()) {
            log.warn("Intento de generar factura sin detalles para usuarioRut={}", usuarioRut);
            throw new BusinessException("La factura debe tener al menos un detalle.");
        }

        Factura factura = Factura.builder()
                .numero("FCT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .fechaEmision(LocalDate.now())
                .estado("EMITIDA")
                .pedidoId(pedidoId)
                .usuarioRut(usuarioRut)
                .total(0)
                .build();

        Factura saved = facturaRepository.save(factura);

        for (DetalleFactura detalle : detalles) {
            detalle.calcularSubtotal();
            detalle.setFactura(saved);
            detalleFacturaRepository.save(detalle);
            saved.getDetalles().add(detalle);
        }

        saved.setTotal(detalles.stream().mapToDouble(DetalleFactura::getSubtotal).sum());
        Factura resultado = facturaRepository.save(saved);
        log.info("Factura generada: numero={}, id={}, total={}", resultado.getNumero(), resultado.getIdFactura(), resultado.getTotal());
        return resultado;
    }

    public double calcularTotal(Long idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + idFactura));
        return detalleFacturaRepository.findByFactura(factura)
                .stream()
                .mapToDouble(DetalleFactura::getSubtotal)
                .sum();
    }

    public Factura anularFactura(Long idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + idFactura));
        if ("ANULADA".equalsIgnoreCase(factura.getEstado())) {
            log.warn("Intento de anular una factura ya anulada: id={}", idFactura);
            throw new BusinessException("La factura ya está anulada.");
        }
        factura.setEstado("ANULADA");
        Factura anulada = facturaRepository.save(factura);
        log.info("Factura anulada: id={}", idFactura);
        return anulada;
    }

    public Map<String, Object> obtenerFacturaConUsuario(Long idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + idFactura));
        Map<String, Object> usuario = clienteFeignClient.obtenerUsuarioPorRut(factura.getUsuarioRut());
        return Map.of("factura", factura, "usuario", usuario);
    }
}
