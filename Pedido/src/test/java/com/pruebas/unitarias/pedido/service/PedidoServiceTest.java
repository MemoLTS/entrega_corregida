package com.pruebas.unitarias.pedido.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebas.unitarias.pedido.model.DetallePedido;
import com.pruebas.unitarias.pedido.model.Pedido;
import com.pruebas.unitarias.pedido.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
      @Mock
    private PedidoRepository repository;

    @InjectMocks
    private PedidoService service;

    private Pedido pedido;

    @BeforeEach
    void setUp() {

        pedido = new Pedido();

        pedido.setCliente("Juan Pérez");
        pedido.setEstado("PENDIENTE");

        DetallePedido detalle =
                new DetallePedido();

        detalle.setProducto("Botella Ecológica");
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(5000.0);

        pedido.setDetalles(
                Arrays.asList(detalle));
    }

    @Test
    void deberiaCrearPedido() {

        when(repository.save(any()))
                .thenReturn(pedido);

        Pedido resultado =
                service.crearPedido(pedido);

        assertEquals(
                "PENDIENTE",
                resultado.getEstado());
    }

    @Test
    void deberiaConsultarPedido() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(pedido));

        Pedido resultado =
                service.consultarPedido(1L);

        assertNotNull(resultado);
    }

    @Test
    void deberiaCalcularTotalPedido() {

        Double total =
                service.calcularTotalPedido(
                        pedido);

        assertEquals(
                10000.0,
                total);
    }

    @Test
    void deberiaActualizarEstadoPedido() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(repository.save(any()))
                .thenReturn(pedido);

        Pedido resultado =
                service.actualizarEstado(
                        1L,
                        "PAGADO");

        assertEquals(
                "PAGADO",
                resultado.getEstado());
    }

    @Test
    void deberiaEliminarPedido() {

        doNothing()
                .when(repository)
                .deleteById(1L);

        service.eliminarPedido(1L);

        verify(repository)
                .deleteById(1L);
    }

    @Test
    void deberiaLanzarExcepcionSiPedidoNoExiste() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.consultarPedido(1L)
                );

        assertEquals(
                "Pedido no encontrado",
                exception.getMessage());
    }
}
