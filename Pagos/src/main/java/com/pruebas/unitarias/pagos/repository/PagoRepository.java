package com.pruebas.unitarias.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.unitarias.pagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{
    
}
