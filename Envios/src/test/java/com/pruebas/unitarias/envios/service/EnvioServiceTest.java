package com.pruebas.unitarias.envios.service;

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

import com.pruebas.unitarias.envios.model.Envio;
import com.pruebas.unitarias.envios.repository.EnvioRepository;

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTest {
    
     @Mock
    private EnvioRepository repository;

    @InjectMocks
    private EnvioService service;

    private Envio envio;

    @BeforeEach
    void setUp() {

        envio = new Envio();

        envio.setId(1L);
        envio.setPedidoId(100L);
        envio.setDireccionDestino(
                "Av. Siempre Viva 123");

        envio.setTransportista(
                "EcoTransport");

        envio.setEstado("PENDIENTE");
    }

    @Test
    void deberiaCrearEnvio() {

        when(repository.save(any()))
                .thenReturn(envio);

        Envio resultado =
                service.crearEnvio(envio);

        assertEquals(
                "PENDIENTE",
                resultado.getEstado());

        verify(repository)
                .save(any(Envio.class));
    }

    @Test
    void deberiaConsultarEnvio() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(envio));

        Envio resultado =
                service.consultarEnvio(1L);

        assertNotNull(resultado);

        assertEquals(
                100L,
                resultado.getPedidoId());
    }

    @Test
    void deberiaActualizarEstado() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(envio));

        when(repository.save(any()))
                .thenReturn(envio);

        Envio resultado =
                service.actualizarEstado(
                        1L,
                        "EN_CAMINO");

        assertEquals(
                "EN_CAMINO",
                resultado.getEstado());
    }

    @Test
    void deberiaConfirmarEntrega() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(envio));

        when(repository.save(any()))
                .thenReturn(envio);

        Envio resultado =
                service.confirmarEntrega(1L);

        assertEquals(
                "ENTREGADO",
                resultado.getEstado());
    }

    @Test
    void deberiaEliminarEnvio() {

        doNothing()
                .when(repository)
                .deleteById(1L);

        service.eliminarEnvio(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiEnvioNoExiste() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.consultarEnvio(1L)
                );

        assertEquals(
                "Envío no encontrado",
                exception.getMessage());
    }
}
