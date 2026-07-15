package com.tiendat.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendat.tienda.model.HorarioPersonal;
@Repository
public interface HorarioPersonalRepository extends JpaRepository<HorarioPersonal, Long> {
    List<HorarioPersonal> findByAsignacion_IdAsignacion(Long idAsignacion);

}