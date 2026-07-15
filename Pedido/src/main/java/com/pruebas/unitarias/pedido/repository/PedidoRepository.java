package com.pruebas.unitarias.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.unitarias.pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
