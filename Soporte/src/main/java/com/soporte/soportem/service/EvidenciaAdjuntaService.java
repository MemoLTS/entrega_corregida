package com.soporte.soportem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soporte.soportem.dto.EvidenciaAdjuntaDTO;
import com.soporte.soportem.exception.ResourceNotFoundException;
import com.soporte.soportem.model.EvidenciaAdjunta;
import com.soporte.soportem.model.TicketSoporte;
import com.soporte.soportem.repository.EvidenciaAdjuntaRepository;
import com.soporte.soportem.repository.TicketSoporteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor

public class EvidenciaAdjuntaService {

    private final EvidenciaAdjuntaRepository evidenciaAdjuntaRepository;
    private final TicketSoporteRepository ticketSoporteRepository;

    public List<EvidenciaAdjunta> listarPorIdTicket(Long idTicket) {
        return evidenciaAdjuntaRepository.findByTicketSoporteIdTicket(idTicket);
    }

    public EvidenciaAdjunta obtenerEvidenciaPorId(Long idEvidencia) {
        return evidenciaAdjuntaRepository.findById(idEvidencia)
            .orElseThrow(() -> new ResourceNotFoundException("Evidencia", idEvidencia));
    }

    public EvidenciaAdjunta adjuntarEvidencia(Long idTicket, EvidenciaAdjuntaDTO dto) {
        log.info("Adjuntando evidencia al ticket {}", idTicket);

        TicketSoporte ticket = ticketSoporteRepository.findById(idTicket)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket", idTicket));

        EvidenciaAdjunta evidencia = EvidenciaAdjunta.builder()
            .nombreArchivo(dto.getNombreArchivo())
            .tipoArchivo(dto.getTipoArchivo())
            .urlArchivo(dto.getUrlArchivo())
            .fechaCarga(LocalDateTime.now())
            .ticketSoporte(ticket)
            .build();

        return evidenciaAdjuntaRepository.save(evidencia);
    }

    public void eliminarEvidencia(Long id) {
        log.info("Eliminando evidencia {}", id);
        if (!evidenciaAdjuntaRepository.existsById(id))
            throw new ResourceNotFoundException("Evidencia", id);
        evidenciaAdjuntaRepository.deleteById(id);
    }

}
