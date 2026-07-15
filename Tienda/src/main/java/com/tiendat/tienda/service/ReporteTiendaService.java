package com.tiendat.tienda.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendat.tienda.dto.ReporteTiendaRequestDTO;
import com.tiendat.tienda.dto.ReporteTiendaResponseDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.ReporteTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.ReporteTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional

public class ReporteTiendaService {

    @Autowired
    private ReporteTiendaRepository reporteTiendaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    //Crear reporte de la tienda
    public ReporteTiendaResponseDTO crearReporteTienda(ReporteTiendaRequestDTO request){
        Tienda tienda = buscarTienda(request.getIdTienda());

        ReporteTienda reporte = new ReporteTienda();
        reporte.setTienda(tienda);
        reporte.setTipoReporte(request.getTipoReporte());
        reporte.setPeriodoInicio(request.getPeriodoInicio());
        reporte.setPeriodoFin(request.getPeriodoFin());
        reporte.setFechaGeneracion(LocalDateTime.now());

        ReporteTienda creado = reporteTiendaRepository.save(reporte);
        log.info("Reporte creado con id {}", creado.getIdReporte());
        return toResponse(creado);
    }

    //Listar todos los reportes de la tienda
    public List<ReporteTiendaResponseDTO> listarReporteTienda(){
        return reporteTiendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    //Listar reporte por id de tienda
    public List<ReporteTiendaResponseDTO> listarPorTiendaReporte(Long idTienda){
        return reporteTiendaRepository.findByTienda_IdTienda(idTienda).stream().map(this::toResponse).toList();
    }

    //Modificar reporte de la tienda por el id
    public ReporteTiendaResponseDTO modificarReporte(Long id, ReporteTiendaRequestDTO request){
        ReporteTienda existente = buscarEntidad(id);
        existente.setTipoReporte(request.getTipoReporte());
        existente.setPeriodoInicio(request.getPeriodoInicio());
        existente.setPeriodoFin(request.getPeriodoFin());
        ReporteTienda actualizado = reporteTiendaRepository.save(existente);
        log.info("Reporte {} actualizado", id);
        return toResponse(actualizado);
    }

    //Eliminar reporte de la tienda por id
    public void eliminarReporte(Long id){
        if (!reporteTiendaRepository.existsById(id)) {
            log.warn("Reporte con id {} no encontrado", id);
            throw new RecursoNoEncontradoException("Reporte con id " + id + " no encontrado");
        }
        reporteTiendaRepository.deleteById(id);
        log.info("Reporte {} eliminado", id);
    }

    private Tienda buscarTienda(Long idTienda){
        return tiendaRepository.findById(idTienda)
                .orElseThrow(() -> {
                    log.warn("Tienda con id {} no encontrada", idTienda);
                    return new RecursoNoEncontradoException("Tienda con id " + idTienda + " no encontrada");
                });
    }

    private ReporteTienda buscarEntidad(Long id){
        return reporteTiendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Reporte con id {} no encontrado", id);
                    return new RecursoNoEncontradoException("Reporte con id " + id + " no encontrado");
                });
    }

    private ReporteTiendaResponseDTO toResponse(ReporteTienda reporte){
        return new ReporteTiendaResponseDTO(
                reporte.getIdReporte(),
                reporte.getTienda().getIdTienda(),
                reporte.getTipoReporte(),
                reporte.getPeriodoInicio(),
                reporte.getPeriodoFin(),
                reporte.getFechaGeneracion());
    }

}
