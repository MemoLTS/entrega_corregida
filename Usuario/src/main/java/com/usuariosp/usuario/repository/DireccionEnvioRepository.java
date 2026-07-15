package com.usuariosp.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usuariosp.usuario.model.DireccionEnvio;

public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Long> {
    List<DireccionEnvio> findByActivaTrue();
    List<DireccionEnvio> findByUsuarioRut(Long rut);
    List<DireccionEnvio> findByUsuarioRutAndActivaTrue(Long rut);
}
