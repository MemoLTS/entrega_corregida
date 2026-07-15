package com.tiendat.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendat.tienda.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository <Tienda, Long>{
    
}
