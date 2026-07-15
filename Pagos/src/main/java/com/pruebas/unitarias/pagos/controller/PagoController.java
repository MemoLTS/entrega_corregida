package com.pruebas.unitarias.pagos.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.unitarias.pagos.model.Pago;
import com.pruebas.unitarias.pagos.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final PagoService service;

    public PagoController(
            PagoService service) {

        this.service = service;
    }

    @PostMapping
    public Pago registrar(
            @RequestBody Pago pago) {

        return service.registrarPago(pago);
    }

    @GetMapping("/{id}")
    public Pago consultar(
            @PathVariable Long id) {

        return service.consultarPago(id);
    }

    @PutMapping("/{id}/confirmar")
    public Pago confirmar(
            @PathVariable Long id) {

        return service.confirmarPago(id);
    }

    @PutMapping("/{id}/rechazar")
    public Pago rechazar(
            @PathVariable Long id) {

        return service.rechazarPago(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        service.eliminarPago(id);
    }
}
