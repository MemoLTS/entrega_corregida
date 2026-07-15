package com.pruebas.unitarias.envios.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.unitarias.envios.model.Envio;
import com.pruebas.unitarias.envios.service.EnvioService;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {
    
    private final EnvioService service;

    public EnvioController(
            EnvioService service) {

        this.service = service;
    }

    @PostMapping
    public Envio crear(
            @RequestBody Envio envio) {

        return service.crearEnvio(envio);
    }

    @GetMapping("/{id}")
    public Envio consultar(
            @PathVariable Long id) {

        return service.consultarEnvio(id);
    }

    @PutMapping("/{id}/estado")
    public Envio actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        return service.actualizarEstado(
                id,
                estado);
    }

    @PutMapping("/{id}/confirmar")
    public Envio confirmarEntrega(
            @PathVariable Long id) {

        return service.confirmarEntrega(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        service.eliminarEnvio(id);
    }
}
