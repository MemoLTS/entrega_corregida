package com.facturacion.facturacionm.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.facturacion.facturacionm.exception.ResourceNotFoundException;
import com.facturacion.facturacionm.model.DetalleFactura;
import com.facturacion.facturacionm.model.Factura;
import com.facturacion.facturacionm.repository.DetalleFacturaRepository;
import com.facturacion.facturacionm.repository.FacturaRepository;

@Service
public class DetalleFacturaService {

    private static final Logger log = LoggerFactory.getLogger(DetalleFacturaService.class);

    private final DetalleFacturaRepository detalleFacturaRepository;
    private final FacturaRepository facturaRepository;

    public DetalleFacturaService(DetalleFacturaRepository detalleFacturaRepository,
                                  FacturaRepository facturaRepository) {
        this.detalleFacturaRepository = detalleFacturaRepository;
        this.facturaRepository = facturaRepository;
    }

    public List<DetalleFactura> listarTodosDetallesF() {
        return detalleFacturaRepository.findAll();
    }

    public Optional<DetalleFactura> obtenerPorId(Long idDetalleFactura) {
        return detalleFacturaRepository.findById(idDetalleFactura);
    }

    public List<DetalleFactura> listarPorFactura(Long idFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + idFactura));
        return detalleFacturaRepository.findByFactura(factura);
    }

    public void eliminarDetalleFactura(Long idDetalleFactura) {
        if (!detalleFacturaRepository.existsById(idDetalleFactura)) {
            log.warn("Intento de eliminar detalle inexistente: id={}", idDetalleFactura);
            throw new ResourceNotFoundException("Detalle no encontrado: " + idDetalleFactura);
        }
        detalleFacturaRepository.deleteById(idDetalleFactura);
        log.info("Detalle de factura eliminado: id={}", idDetalleFactura);
    }

    public DetalleFactura calcularSubTotal(Long idDetalleFactura) {
        DetalleFactura detalle = detalleFacturaRepository.findById(idDetalleFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado: " + idDetalleFactura));
        detalle.calcularSubtotal();
        DetalleFactura actualizado = detalleFacturaRepository.save(detalle);
        log.info("Subtotal recalculado: idDetalle={}, subtotal={}", idDetalleFactura, actualizado.getSubtotal());
        return actualizado;
    }

    public DetalleFactura agregarDetalle(Long idFactura, DetalleFactura detalleFactura) {
        Factura factura = facturaRepository.findById(idFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + idFactura));
        detalleFactura.setFactura(factura);
        detalleFactura.calcularSubtotal();
        DetalleFactura guardado = detalleFacturaRepository.save(detalleFactura);
        log.info("Detalle agregado a factura id={}: descripcion={}, subtotal={}", idFactura, guardado.getDescripcion(), guardado.getSubtotal());
        return guardado;
    }
}
