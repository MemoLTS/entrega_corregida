package com.tiendat.tienda.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendat.tienda.dto.TiendaRequestDTO;
import com.tiendat.tienda.dto.TiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.TiendaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional

public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public TiendaResponseDTO crearTienda(TiendaRequestDTO request){
        Tienda tienda = new Tienda();
        tienda.setNombre(request.getNombre());
        tienda.setCodigoTienda(request.getCodigoTienda());
        tienda.setDireccion(request.getDireccion());
        tienda.setComuna(request.getComuna());
        tienda.setCiudad(request.getCiudad());
        tienda.setRegion(request.getRegion());
        tienda.setTelefono(request.getTelefono());
        tienda.setFechaCreacion(LocalDateTime.now());
        tienda.setEstado("ACTIVA");
        Tienda creada = tiendaRepository.save(tienda);
        log.info("Tienda creada con id {}", creada.getIdTienda());
        return toResponse(creada);
    }

    public List<TiendaResponseDTO> listarTiendas(){
        return tiendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TiendaResponseDTO buscarTiendaPorId(Long idTienda){
        return toResponse(buscarEntidad(idTienda));
    }

    public TiendaResponseDTO modificarTienda(Long id, TiendaRequestDTO request){
        Tienda tiendaExistente = buscarEntidad(id);
        tiendaExistente.setNombre(request.getNombre());
        tiendaExistente.setCodigoTienda(request.getCodigoTienda());
        tiendaExistente.setDireccion(request.getDireccion());
        tiendaExistente.setComuna(request.getComuna());
        tiendaExistente.setCiudad(request.getCiudad());
        tiendaExistente.setRegion(request.getRegion());
        tiendaExistente.setTelefono(request.getTelefono());
        Tienda actualizada = tiendaRepository.save(tiendaExistente);
        log.info("Tienda {} actualizada", id);
        return toResponse(actualizada);
    }

    public TiendaResponseDTO desactivarTienda(Long id){
        Tienda tiendaExistente = buscarEntidad(id);
        tiendaExistente.setEstado("INACTIVA");
        Tienda desactivada = tiendaRepository.save(tiendaExistente);
        log.info("Tienda {} desactivada", id);
        return toResponse(desactivada);
    }

    public void eliminarTienda(Long idTienda){
        if (!tiendaRepository.existsById(idTienda)) {
            log.warn("Tienda con id {} no encontrada", idTienda);
            throw new RecursoNoEncontradoException("Tienda con id " + idTienda + " no encontrada");
        }
        tiendaRepository.deleteById(idTienda);
        log.info("Tienda {} eliminada", idTienda);
    }

    private Tienda buscarEntidad(Long idTienda){
        return tiendaRepository.findById(idTienda)
                .orElseThrow(() -> {
                    log.warn("Tienda con id {} no encontrada", idTienda);
                    return new RecursoNoEncontradoException("Tienda con id " + idTienda + " no encontrada");
                });
    }

    private TiendaResponseDTO toResponse(Tienda tienda){
        return new TiendaResponseDTO(
                tienda.getIdTienda(),
                tienda.getNombre(),
                tienda.getCodigoTienda(),
                tienda.getDireccion(),
                tienda.getComuna(),
                tienda.getCiudad(),
                tienda.getRegion(),
                tienda.getTelefono(),
                tienda.getEstado(),
                tienda.getFechaCreacion());
    }
}
