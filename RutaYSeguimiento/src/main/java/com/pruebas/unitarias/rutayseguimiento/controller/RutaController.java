package com.pruebas.unitarias.rutayseguimiento.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;
import com.pruebas.unitarias.rutayseguimiento.service.RutaService;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {
    
    private final RutaService service;

    public RutaController(
            RutaService service) {

        this.service = service;
    }

    @PostMapping
    public Ruta crear(
            @RequestBody Ruta ruta) {

        return service.crearRuta(ruta);
    }

    @GetMapping("/{id}")
    public Ruta consultar(
            @PathVariable Long id) {

        return service.consultarRuta(id);
    }

    @PutMapping("/{id}/ubicacion")
    public Ruta actualizarUbicacion(
            @PathVariable Long id,
            @RequestParam String ubicacion) {

        return service.actualizarUbicacion(
                id,
                ubicacion);
    }

    @PutMapping("/{id}/seguimiento")
    public Ruta registrarSeguimiento(
            @PathVariable Long id,
            @RequestParam String estado) {

        return service.registrarSeguimiento(
                id,
                estado);
    }

    @PutMapping("/{id}/finalizar")
    public Ruta finalizarRuta(
            @PathVariable Long id) {

        return service.finalizarRuta(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        service.eliminarRuta(id);
    }
}
