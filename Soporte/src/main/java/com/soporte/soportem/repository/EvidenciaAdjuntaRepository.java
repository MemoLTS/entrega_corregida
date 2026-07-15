package com.soporte.soportem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soportem.model.EvidenciaAdjunta;

@Repository
public interface EvidenciaAdjuntaRepository extends JpaRepository<EvidenciaAdjunta, Long> {

    List<EvidenciaAdjunta> findByTicketSoporteIdTicket(Long idTicket);

}
