package com.tiendat.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendat.tienda.dto.AsignacionPersonalRequestDTO;
import com.tiendat.tienda.dto.AsignacionPersonalResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.AsignacionPersonal;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.AsignacionPersonalRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional

public class AsignacionPersonalService {

    @Autowired
    private AsignacionPersonalRepository asignacionPersonalRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    //Crear una asignacion de un personal para la tienda
    public AsignacionPersonalResponseDTO crearAsignacionP(AsignacionPersonalRequestDTO request){
        Tienda tienda = buscarTienda(request.getIdTienda());

        AsignacionPersonal asignacion = new AsignacionPersonal();
        asignacion.setTienda(tienda);
        asignacion.setIdEmpleado(request.getIdEmpleado());
        asignacion.setNombreEmpleado(request.getNombreEmpleado());
        asignacion.setCargo(request.getCargo());
        asignacion.setFechaInicio(request.getFechaInicio());
        asignacion.setFechaTermino(request.getFechaTermino());
        asignacion.setEstadoAsignacion("ACTIVA");

        AsignacionPersonal creada = asignacionPersonalRepository.save(asignacion);
        log.info("Asignacion de personal creada con id {}", creada.getIdAsignacion());
        return toResponse(creada);
    }

    //Listar todos las asignaciones del personal de la tienda
    public List<AsignacionPersonalResponseDTO> listarAsignacionPersonal(){
        return asignacionPersonalRepository.findAll().stream().map(this::toResponse).toList();
    }

    //Listar todas las asignaciones del personal de la tienda por id de tienda
    public List<AsignacionPersonalResponseDTO> listarPorTiendaAsignacionP(Long idTienda){
        return asignacionPersonalRepository.findByTienda_IdTienda(idTienda).stream().map(this::toResponse).toList();
    }

    //Modificar la asignacion del personal de la tienda por su id
    public AsignacionPersonalResponseDTO modificarAsignacionP(Long idAsignacionPersonal, AsignacionPersonalRequestDTO request){
        AsignacionPersonal existente = buscarEntidad(idAsignacionPersonal);
        existente.setNombreEmpleado(request.getNombreEmpleado());
        existente.setCargo(request.getCargo());
        existente.setFechaInicio(request.getFechaInicio());
        existente.setFechaTermino(request.getFechaTermino());
        AsignacionPersonal actualizada = asignacionPersonalRepository.save(existente);
        log.info("Asignacion {} actualizada", idAsignacionPersonal);
        return toResponse(actualizada);
    }

    //Desactivar la asignacion del personal de la tienda por su id
    public AsignacionPersonalResponseDTO desactivarAsignacionP(Long id){
        AsignacionPersonal existente = buscarEntidad(id);
        existente.setEstadoAsignacion("INACTIVA");
        AsignacionPersonal desactivada = asignacionPersonalRepository.save(existente);
        log.info("Asignacion {} desactivada", id);
        return toResponse(desactivada);
    }

    //Eliminar la asignacion del personal de la tienda por su id
    public void eliminarAsignacionP(Long id){
        if (!asignacionPersonalRepository.existsById(id)) {
            log.warn("Asignacion con id {} no encontrada", id);
            throw new RecursoNoEncontradoException("Asignacion con id " + id + " no encontrada");
        }
        asignacionPersonalRepository.deleteById(id);
        log.info("Asignacion {} eliminada", id);
    }

    private Tienda buscarTienda(Long idTienda){
        return tiendaRepository.findById(idTienda)
                .orElseThrow(() -> {
                    log.warn("Tienda con id {} no encontrada", idTienda);
                    return new RecursoNoEncontradoException("Tienda con id " + idTienda + " no encontrada");
                });
    }

    private AsignacionPersonal buscarEntidad(Long id){
        return asignacionPersonalRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Asignacion con id {} no encontrada", id);
                    return new RecursoNoEncontradoException("Asignacion con id " + id + " no encontrada");
                });
    }

    private AsignacionPersonalResponseDTO toResponse(AsignacionPersonal asignacion){
        return new AsignacionPersonalResponseDTO(
                asignacion.getIdAsignacion(),
                asignacion.getTienda().getIdTienda(),
                asignacion.getIdEmpleado(),
                asignacion.getNombreEmpleado(),
                asignacion.getCargo(),
                asignacion.getFechaInicio(),
                asignacion.getFechaTermino(),
                asignacion.getEstadoAsignacion());
    }

}
