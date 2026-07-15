package com.tiendat.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendat.tienda.dto.HorarioPersonalRequestDTO;
import com.tiendat.tienda.dto.HorarioPersonalResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.AsignacionPersonal;
import com.tiendat.tienda.model.HorarioPersonal;
import com.tiendat.tienda.repository.AsignacionPersonalRepository;
import com.tiendat.tienda.repository.HorarioPersonalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional

public class HorarioPersonalService {

    @Autowired
    private HorarioPersonalRepository horarioPersonalRepository;

    @Autowired
    private AsignacionPersonalRepository asignacionPersonalRepository;

    //Crear el horario del personal de la tienda
    public HorarioPersonalResponseDTO crear(HorarioPersonalRequestDTO request){
        AsignacionPersonal asignacion = buscarAsignacion(request.getIdAsignacion());

        HorarioPersonal horario = new HorarioPersonal();
        horario.setAsignacion(asignacion);
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraTermino(request.getHoraTermino());
        horario.setTurno(request.getTurno());
        horario.setActivo(true);

        HorarioPersonal creado = horarioPersonalRepository.save(horario);
        log.info("Horario de personal creado con id {}", creado.getIdHorarioPersonal());
        return toResponse(creado);
    }

    //Listar todos los horarios del personal de la tienda
    public List<HorarioPersonalResponseDTO> listar(){
        return horarioPersonalRepository.findAll().stream().map(this::toResponse).toList();
    }

    //Listar los horarios del personal por id de asignacion
    public List<HorarioPersonalResponseDTO> listarPorAsignacion(Long idAsignacion){
        return horarioPersonalRepository.findByAsignacion_IdAsignacion(idAsignacion).stream().map(this::toResponse).toList();
    }

    //Modificar el horario del personal de la tienda por el id
    public HorarioPersonalResponseDTO modificar(Long id, HorarioPersonalRequestDTO request){
        HorarioPersonal existente = buscarEntidad(id);
        existente.setDiaSemana(request.getDiaSemana());
        existente.setHoraInicio(request.getHoraInicio());
        existente.setHoraTermino(request.getHoraTermino());
        existente.setTurno(request.getTurno());
        HorarioPersonal actualizado = horarioPersonalRepository.save(existente);
        log.info("Horario de personal {} actualizado", id);
        return toResponse(actualizado);
    }

    //Desactivar el horario del personal de la tienda por el id
    public HorarioPersonalResponseDTO desactivar(Long id){
        HorarioPersonal existente = buscarEntidad(id);
        existente.setActivo(false);
        HorarioPersonal desactivado = horarioPersonalRepository.save(existente);
        log.info("Horario de personal {} desactivado", id);
        return toResponse(desactivado);
    }

    //Eliminar el horario del personal de la tienda por id
    public void eliminar(Long id){
        if (!horarioPersonalRepository.existsById(id)) {
            log.warn("Horario de personal con id {} no encontrado", id);
            throw new RecursoNoEncontradoException("Horario de personal con id " + id + " no encontrado");
        }
        horarioPersonalRepository.deleteById(id);
        log.info("Horario de personal {} eliminado", id);
    }

    private AsignacionPersonal buscarAsignacion(Long idAsignacion){
        return asignacionPersonalRepository.findById(idAsignacion)
                .orElseThrow(() -> {
                    log.warn("Asignacion con id {} no encontrada", idAsignacion);
                    return new RecursoNoEncontradoException("Asignacion con id " + idAsignacion + " no encontrada");
                });
    }

    private HorarioPersonal buscarEntidad(Long id){
        return horarioPersonalRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Horario de personal con id {} no encontrado", id);
                    return new RecursoNoEncontradoException("Horario de personal con id " + id + " no encontrado");
                });
    }

    private HorarioPersonalResponseDTO toResponse(HorarioPersonal horario){
        return new HorarioPersonalResponseDTO(
                horario.getIdHorarioPersonal(),
                horario.getAsignacion().getIdAsignacion(),
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraTermino(),
                horario.getTurno(),
                horario.getActivo());
    }

}
