package com.pruebas.unitarias.rutayseguimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;

public interface RutaRepository  extends JpaRepository<Ruta, Long> {
    
}
