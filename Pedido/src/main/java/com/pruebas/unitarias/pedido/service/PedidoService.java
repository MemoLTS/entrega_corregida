package com.pruebas.unitarias.pedido.service;

import org.springframework.stereotype.Service;

import com.pruebas.unitarias.pedido.model.DetallePedido;
import com.pruebas.unitarias.pedido.model.Pedido;
import com.pruebas.unitarias.pedido.repository.PedidoRepository;

@Service
public class PedidoService {
     private final PedidoRepository repository;

    public PedidoService(
            PedidoRepository repository) {

        this.repository = repository;
    }

    public Pedido crearPedido(
            Pedido pedido) {

        pedido.setEstado("PENDIENTE");

        calcularTotalPedido(pedido);

        return repository.save(pedido);
    }

    public Pedido consultarPedido(
            Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido no encontrado"));
    }

    public Double calcularTotalPedido(
            Pedido pedido) {

        Double total = 0.0;

        for (DetallePedido detalle :
                pedido.getDetalles()) {

            detalle.setSubtotal(
                    detalle.getCantidad()
                            * detalle.getPrecioUnitario());

            total += detalle.getSubtotal();
        }

        pedido.setTotal(total);

        return total;
    }

    public Pedido actualizarEstado(
            Long id,
            String estado) {

        Pedido pedido =
                consultarPedido(id);

        pedido.setEstado(estado);

        return repository.save(pedido);
    }

    public void eliminarPedido(
            Long id) {

        repository.deleteById(id);
    }
}
