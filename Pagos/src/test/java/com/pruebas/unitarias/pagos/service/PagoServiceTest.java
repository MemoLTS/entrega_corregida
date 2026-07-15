package com.pruebas.unitarias.pagos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.unitarias.pagos.model.Pago;
import com.pruebas.unitarias.pagos.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {
    @Mock
    private PagoRepository repository;

    @InjectMocks
    private PagoService service;

    private Pago pago;

    @BeforeEach
    void setUp() {

        pago = new Pago();

        pago.setId(1L);
        pago.setFacturaId(100L);
        pago.setMonto(15000.0);
        pago.setMetodoPago("TARJETA");
        pago.setEstado("REGISTRADO");
    }

    @Test
    void deberiaRegistrarPago() {

        when(repository.save(any()))
                .thenReturn(pago);

        Pago resultado =
                service.registrarPago(pago);

        assertEquals(
                "REGISTRADO",
                resultado.getEstado());

        verify(repository)
                .save(any(Pago.class));
    }

    @Test
    void deberiaConsultarPago() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(pago));

        Pago resultado =
                service.consultarPago(1L);

        assertNotNull(resultado);

        assertEquals(
                15000.0,
                resultado.getMonto());
    }

    @Test
    void deberiaConfirmarPago() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(pago));

        when(repository.save(any()))
                .thenReturn(pago);

        Pago resultado =
                service.confirmarPago(1L);

        assertEquals(
                "CONFIRMADO",
                resultado.getEstado());
    }

    @Test
    void deberiaRechazarPago() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(pago));

        when(repository.save(any()))
                .thenReturn(pago);

        Pago resultado =
                service.rechazarPago(1L);

        assertEquals(
                "RECHAZADO",
                resultado.getEstado());
    }

    @Test
    void deberiaEliminarPago() {

        doNothing()
                .when(repository)
                .deleteById(1L);

        service.eliminarPago(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiPagoNoExiste() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.consultarPago(1L)
                );

        assertEquals(
                "Pago no encontrado",
                exception.getMessage());
    }
}
