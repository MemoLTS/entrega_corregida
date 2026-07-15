package com.pruebas.unitariasproveedor.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.pruebas.unitariasproveedor.model.Proveedor;

@DataJpaTest
public class ProveedorRepositoryIT {
    
     @Autowired
    private ProveedorRepository repository;

    @Test
    void deberiaGuardarProveedor() {

        Proveedor proveedor =
                new Proveedor();

        proveedor.setNombre(
                "Eco Supplier");

        proveedor.setContacto(
                "contacto@eco.cl");

        proveedor.setTelefono(
                "987654321");

        Proveedor guardado =
                repository.save(proveedor);

        assertNotNull(
                guardado.getId());

        assertEquals(
                "Eco Supplier",
                guardado.getNombre());
    }
}
