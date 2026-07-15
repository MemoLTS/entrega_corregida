package com.soporte.soportem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soportem.model.PersonalSoporte;

@Repository

public interface  PersonalSoporteRepository extends JpaRepository<PersonalSoporte, Long>{

    Optional<PersonalSoporte> findByEmail(String email);
    List<PersonalSoporte> findByEstado(String estado);
    List<PersonalSoporte> findByRol(String rol);
    
}
