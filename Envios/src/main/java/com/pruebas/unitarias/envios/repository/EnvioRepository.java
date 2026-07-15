package com.pruebas.unitarias.envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.unitarias.envios.model.Envio;

public interface EnvioRepository extends JpaRepository<Envio, Long>{
    
}
