package com.pruebas.unitariasproveedor.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.unitariasproveedor.model.Proveedor;
import com.pruebas.unitariasproveedor.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {
    private final ProveedorService service;

    public ProveedorController(
            ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Proveedor> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Proveedor buscar(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Proveedor guardar(
            @RequestBody Proveedor proveedor) {

        return service.guardar(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizar(
            @PathVariable Long id,
            @RequestBody Proveedor proveedor) {

        return service.actualizar(id,
                                  proveedor);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        service.eliminar(id);
    }
}
