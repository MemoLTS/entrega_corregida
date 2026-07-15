package com.soporte.soportem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soporte.soportem.model.MensajeSoporte;

@Repository
public interface MensajeSoporteRepository extends JpaRepository<MensajeSoporte, Long> {

    List<MensajeSoporte> findByTicketSoporteIdTicket(Long idTicket);

}
