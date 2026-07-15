package com.pruebas.unitariasproveedor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.unitariasproveedor.model.Proveedor;
import com.pruebas.unitariasproveedor.repository.ProveedorRepository;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {
     @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorService service;

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {

        proveedor = new Proveedor(
                1L,
                "EcoProveedor SPA",
                "11.111.111-1",
                "987654321",
                "Santiago"
        );
    }

    @Test
    void deberiaGuardarProveedor() {

        when(repository.save(any()))
                .thenReturn(proveedor);

        Proveedor resultado =
                service.guardar(proveedor);

        assertNotNull(resultado);
        assertEquals(
                "EcoProveedor SPA",
                resultado.getNombre()
        );
    }

    @Test
    void deberiaListarProveedores() {

        when(repository.findAll())
                .thenReturn(
                        Arrays.asList(
                                proveedor));

        List<Proveedor> resultado =
                service.obtenerTodos();

        assertEquals(1,
                     resultado.size());
    }

    @Test
    void deberiaBuscarProveedorPorId() {

        when(repository.findById(1L))
                .thenReturn(
                        Optional.of(proveedor));

        Proveedor resultado =
                service.obtenerPorId(1L);

        assertEquals(
                1L,
                resultado.getId()
        );
    }

    @Test
    void deberiaActualizarProveedor() {

        Proveedor nuevo =
                new Proveedor();

        nuevo.setNombre(
                "Proveedor Verde");

        when(repository.findById(1L))
                .thenReturn(
                        Optional.of(proveedor));

        when(repository.save(any()))
                .thenReturn(nuevo);

        Proveedor resultado =
                service.actualizar(
                        1L,
                        nuevo);

        assertEquals(
                "Proveedor Verde",
                resultado.getNombre()
        );
    }

    @Test
    void deberiaEliminarProveedor() {

        doNothing().when(repository)
                .deleteById(1L);

        service.eliminar(1L);

        verify(repository)
                .deleteById(1L);
    }
}
