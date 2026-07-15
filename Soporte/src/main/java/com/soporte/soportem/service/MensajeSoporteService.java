package com.soporte.soportem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soporte.soportem.dto.MensajeSoporteDTO;
import com.soporte.soportem.exception.BusinessException;
import com.soporte.soportem.exception.ResourceNotFoundException;
import com.soporte.soportem.model.MensajeSoporte;
import com.soporte.soportem.model.TicketSoporte;
import com.soporte.soportem.repository.MensajeSoporteRepository;
import com.soporte.soportem.repository.TicketSoporteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor

public class MensajeSoporteService {

    private final MensajeSoporteRepository mensajeSoporteRepository;
    private final TicketSoporteRepository ticketSoporteRepository;

    public List<MensajeSoporte> listarPorIdTicket(Long idTicket) {
        return mensajeSoporteRepository.findByTicketSoporteIdTicket(idTicket);
    }

    public MensajeSoporte obtenerMensajePorId(Long idMensaje) {
        return mensajeSoporteRepository.findById(idMensaje)
            .orElseThrow(() -> new ResourceNotFoundException("Mensaje", idMensaje));
    }

    public MensajeSoporte enviarMensaje(Long idTicket, MensajeSoporteDTO dto) {
        log.info("Enviando mensaje al ticket id: {}", idTicket);

        TicketSoporte ticket = ticketSoporteRepository.findById(idTicket)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket", idTicket));

        if ("CERRADO".equals(ticket.getEstadoTicket())) {
            throw new BusinessException("No se pueden enviar mensajes a un ticket cerrado");
        }

        MensajeSoporte mensaje = MensajeSoporte.builder()
            .contenido(dto.getContenido())
            .remitente(dto.getRemitente())
            .tipoRemitente(dto.getTipoRemitente())
            .fechaEnvio(LocalDateTime.now())
            .ticketSoporte(ticket)
            .build();

        return mensajeSoporteRepository.save(mensaje);
    }

    public MensajeSoporte responderMensaje(Long idMensajeOriginal, MensajeSoporteDTO dto) {
        log.info("Respondiendo al mensaje {}", idMensajeOriginal);
        MensajeSoporte original = obtenerMensajePorId(idMensajeOriginal);
        return enviarMensaje(original.getTicketSoporte().getIdTicket(), dto);
    }

    public void eliminarMensaje(Long id) {
        if (!mensajeSoporteRepository.existsById(id))
            throw new ResourceNotFoundException("Mensaje", id);
        mensajeSoporteRepository.deleteById(id);
    }

}
