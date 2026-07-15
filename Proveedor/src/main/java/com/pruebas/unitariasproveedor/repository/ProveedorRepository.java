package com.pruebas.unitariasproveedor.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.unitariasproveedor.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>  {
    
}
