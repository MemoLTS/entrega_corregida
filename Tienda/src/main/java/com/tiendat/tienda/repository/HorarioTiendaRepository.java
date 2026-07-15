package com.tiendat.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendat.tienda.model.HorarioTienda;

@Repository
public interface HorarioTiendaRepository extends JpaRepository<HorarioTienda, Long> {
    List<HorarioTienda> findByTienda_IdTienda(Long idTienda);

}
