package com.usuariosp.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usuariosp.usuario.model.Sesion;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
}

