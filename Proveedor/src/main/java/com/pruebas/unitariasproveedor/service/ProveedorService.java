package com.pruebas.unitariasproveedor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pruebas.unitariasproveedor.model.Proveedor;
import com.pruebas.unitariasproveedor.repository.ProveedorRepository;

@Service
public class ProveedorService {
    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public List<Proveedor> obtenerTodos() {
        return repository.findAll();
    }

    public Proveedor obtenerPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Proveedor no encontrado"));
    }

    public Proveedor guardar(Proveedor proveedor) {
        return repository.save(proveedor);
    }

    public Proveedor actualizar(Long id,
                                Proveedor proveedor) {

        Proveedor existente =
                obtenerPorId(id);

        existente.setNombre(proveedor.getNombre());
        existente.setRut(proveedor.getRut());
        existente.setTelefono(proveedor.getTelefono());
        existente.setDireccion(proveedor.getDireccion());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
