package com.soporte.soportem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soportem.model.SolicitudDevolucion;

@Repository
public interface SolicitudDevolucionRepository extends JpaRepository<SolicitudDevolucion, Long> {

    Optional<SolicitudDevolucion> findByTicketSoporteIdTicket(Long idTicket);
    List<SolicitudDevolucion> findByEstadoSolicitud(String estadoSolicitud);

}
