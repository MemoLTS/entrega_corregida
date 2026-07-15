package com.soporte.soportem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soporte.soportem.dto.ResolucionSoporteDTO;
import com.soporte.soportem.exception.BusinessException;
import com.soporte.soportem.exception.ResourceNotFoundException;
import com.soporte.soportem.model.ResolucionSoporte;
import com.soporte.soportem.model.TicketSoporte;
import com.soporte.soportem.repository.ResolucionSoporteRepository;
import com.soporte.soportem.repository.TicketSoporteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor

public class ResolucionSoporteService {

    private final ResolucionSoporteRepository resolucionSoporteRepository;
    private final TicketSoporteRepository ticketSoporteRepository;

    public List<ResolucionSoporte> listarTodos() {
        return resolucionSoporteRepository.findAll();
    }

    public ResolucionSoporte obtenerPorIdResolucion(Long idResolucion) {
        return resolucionSoporteRepository.findById(idResolucion)
            .orElseThrow(() -> new ResourceNotFoundException("Resolucion de soporte", idResolucion));
    }

    public ResolucionSoporte registrarResolucion(Long idTicket, ResolucionSoporteDTO dto) {
        log.info("Registrando resolucion para ticket: {}", idTicket);

        TicketSoporte ticket = ticketSoporteRepository.findById(idTicket)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket", idTicket));

        if (resolucionSoporteRepository.findByTicketSoporteIdTicket(idTicket).isPresent()) {
            throw new BusinessException("El ticket ya tiene una resolucion registrada");
        }

        ResolucionSoporte resolucion = ResolucionSoporte.builder()
            .tipoResolucion(dto.getTipoResolucion())
            .descripcion(dto.getDescripcion())
            .aprobadoPor(dto.getAprobadoPor())
            .fechaResolucion(LocalDateTime.now())
            .ticketSoporte(ticket)
            .build();

        return resolucionSoporteRepository.save(resolucion);
    }

    public ResolucionSoporte modificarResolucion(Long idResolucion, ResolucionSoporteDTO dto) {
        log.info("Modificando resolucion {}", idResolucion);

        ResolucionSoporte r = obtenerPorIdResolucion(idResolucion);
        r.setTipoResolucion(dto.getTipoResolucion());
        r.setDescripcion(dto.getDescripcion());
        r.setAprobadoPor(dto.getAprobadoPor());
        return resolucionSoporteRepository.save(r);
    }

}
