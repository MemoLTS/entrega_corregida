package com.usuariosp.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usuariosp.usuario.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByPrincipalTrue();
    List<MetodoPago> findByActivoTrue();

    List<MetodoPago> findByUsuarioRut(Long rut);
    List<MetodoPago> findByUsuarioRutAndActivoTrue(Long rut);
}
