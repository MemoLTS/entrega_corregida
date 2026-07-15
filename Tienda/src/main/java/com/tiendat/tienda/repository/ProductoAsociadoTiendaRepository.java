package com.tiendat.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendat.tienda.model.ProductoAsociadoTienda;

@Repository
public interface ProductoAsociadoTiendaRepository extends JpaRepository<ProductoAsociadoTienda, Long> {
    List<ProductoAsociadoTienda> findByTienda_IdTienda(Long idTienda);

}