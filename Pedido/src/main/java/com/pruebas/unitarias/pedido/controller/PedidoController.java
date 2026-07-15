package com.pruebas.unitarias.pedido.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.unitarias.pedido.model.Pedido;
import com.pruebas.unitarias.pedido.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
      private final PedidoService service;

    public PedidoController(
            PedidoService service) {

        this.service = service;
    }

    @PostMapping
    public Pedido crear(
            @RequestBody Pedido pedido) {

        return service.crearPedido(pedido);
    }

    @GetMapping("/{id}")
    public Pedido consultar(
            @PathVariable Long id) {

        return service.consultarPedido(id);
    }

    @PutMapping("/{id}/estado")
    public Pedido actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        return service.actualizarEstado(
                id,
                estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id) {

        service.eliminarPedido(id);
    }
}
