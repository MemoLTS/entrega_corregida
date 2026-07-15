package com.soporte.soportem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soportem.model.TicketSoporte;

@Repository
public interface TicketSoporteRepository extends JpaRepository<TicketSoporte, Long> {

    List<TicketSoporte> findByRunCliente(Long runCliente);
    List<TicketSoporte> findByEstadoTicket(String estadoTicket);
    List<TicketSoporte> findByPrioridad(String prioridad);
    List<TicketSoporte> findByPersonalAsignadoRutPersonalS(Long rutPersonalS);

}
