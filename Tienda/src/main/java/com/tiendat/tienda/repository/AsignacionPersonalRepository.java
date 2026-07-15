package com.tiendat.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendat.tienda.model.AsignacionPersonal;

@Repository
public interface AsignacionPersonalRepository extends JpaRepository<AsignacionPersonal, Long> {
    List<AsignacionPersonal> findByTienda_IdTienda(Long idTienda);

}
