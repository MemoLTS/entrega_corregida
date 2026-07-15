package com.soporte.soportem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soportem.model.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {

    Optional<Reclamo> findByTicketSoporteIdTicket(Long idTicket);
    List<Reclamo> findByEstadoReclamo(String estadoReclamo);

}
