package com.tiendat.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendat.tienda.dto.HorarioTiendaRequestDTO;
import com.tiendat.tienda.dto.HorarioTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.HorarioTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.HorarioTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional

public class HorarioTiendaService {

    @Autowired
    private HorarioTiendaRepository horarioTiendaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    //Para crear el horario de la tienda
    public HorarioTiendaResponseDTO crearHorarioTienda(HorarioTiendaRequestDTO request){
        Tienda tienda = buscarTienda(request.getIdTienda());

        HorarioTienda horario = new HorarioTienda();
        horario.setTienda(tienda);
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraApertura(request.getHoraApertura());
        horario.setHoraCierre(request.getHoraCierre());
        horario.setActivo(true);

        HorarioTienda creado = horarioTiendaRepository.save(horario);
        log.info("Horario de tienda creado con id {}", creado.getIdHorarioTienda());
        return toResponse(creado);
    }

    //Para poder listar todos los horarios creados
    public List<HorarioTiendaResponseDTO> listarHorarioTienda(){
        return horarioTiendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    //Buscar un horario en especifico por el id de tienda
    public List<HorarioTiendaResponseDTO> listarPorTiendaHorario(Long idTienda){
        return horarioTiendaRepository.findByTienda_IdTienda(idTienda).stream().map(this::toResponse).toList();
    }

    //Para modificar el horario de tienda por su id
    public HorarioTiendaResponseDTO modificarHorarioTienda(Long id, HorarioTiendaRequestDTO request){
        HorarioTienda existente = buscarEntidad(id);
        existente.setDiaSemana(request.getDiaSemana());
        existente.setHoraApertura(request.getHoraApertura());
        existente.setHoraCierre(request.getHoraCierre());
        HorarioTienda actualizado = horarioTiendaRepository.save(existente);
        log.info("Horario de tienda {} actualizado", id);
        return toResponse(actualizado);
    }

    //Para desactivar un horario en especifico por su id
    public HorarioTiendaResponseDTO desactivarHorarioTienda(Long idHorarioTienda){
        HorarioTienda existente = buscarEntidad(idHorarioTienda);
        existente.setActivo(false);
        HorarioTienda desactivado = horarioTiendaRepository.save(existente);
        log.info("Horario de tienda {} desactivado", idHorarioTienda);
        return toResponse(desactivado);
    }

    //Para eliminar un horario por el id
    public void eliminarHorarioTienda(Long idHorarioTienda){
        if (!horarioTiendaRepository.existsById(idHorarioTienda)) {
            log.warn("Horario de tienda con id {} no encontrado", idHorarioTienda);
            throw new RecursoNoEncontradoException("Horario de tienda con id " + idHorarioTienda + " no encontrado");
        }
        horarioTiendaRepository.deleteById(idHorarioTienda);
        log.info("Horario de tienda {} eliminado", idHorarioTienda);
    }

    private Tienda buscarTienda(Long idTienda){
        return tiendaRepository.findById(idTienda)
                .orElseThrow(() -> {
                    log.warn("Tienda con id {} no encontrada", idTienda);
                    return new RecursoNoEncontradoException("Tienda con id " + idTienda + " no encontrada");
                });
    }

    private HorarioTienda buscarEntidad(Long id){
        return horarioTiendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Horario de tienda con id {} no encontrado", id);
                    return new RecursoNoEncontradoException("Horario de tienda con id " + id + " no encontrado");
                });
    }

    private HorarioTiendaResponseDTO toResponse(HorarioTienda horario){
        return new HorarioTiendaResponseDTO(
                horario.getIdHorarioTienda(),
                horario.getTienda().getIdTienda(),
                horario.getDiaSemana(),
                horario.getHoraApertura(),
                horario.getHoraCierre(),
                horario.getActivo());
    }

}
