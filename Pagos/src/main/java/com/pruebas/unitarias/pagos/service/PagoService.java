package com.pruebas.unitarias.pagos.service;

import org.springframework.stereotype.Service;

import com.pruebas.unitarias.pagos.model.Pago;
import com.pruebas.unitarias.pagos.repository.PagoRepository;

@Service
public class PagoService {
    private final PagoRepository repository;

    public PagoService(
            PagoRepository repository) {

        this.repository = repository;
    }

    // Registrar Pago

    public Pago registrarPago(
            Pago pago) {

        pago.setEstado("REGISTRADO");

        return repository.save(pago);
    }

    // Consultar Pago

    public Pago consultarPago(
            Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pago no encontrado"));
    }

    // Confirmar Pago

    public Pago confirmarPago(
            Long id) {

        Pago pago =
                consultarPago(id);

        pago.setEstado("CONFIRMADO");

        return repository.save(pago);
    }

    // Rechazar Pago

    public Pago rechazarPago(
            Long id) {

        Pago pago =
                consultarPago(id);

        pago.setEstado("RECHAZADO");

        return repository.save(pago);
    }

    // Eliminar Pago

    public void eliminarPago(
            Long id) {

        repository.deleteById(id);
    }
}
