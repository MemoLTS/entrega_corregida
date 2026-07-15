package com.tiendat.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendat.tienda.model.ReporteTienda;

@Repository
public interface ReporteTiendaRepository extends JpaRepository<ReporteTienda, Long> {
    List<ReporteTienda> findByTienda_IdTienda(Long idTienda);

}