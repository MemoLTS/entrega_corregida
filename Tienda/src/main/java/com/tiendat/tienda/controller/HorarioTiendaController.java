package com.tiendat.tienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendat.tienda.dto.HorarioTiendaRequestDTO;
import com.tiendat.tienda.dto.HorarioTiendaResponseDTO;
import com.tiendat.tienda.service.HorarioTiendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/HorarioTienda")
@CrossOrigin(origins = "*")
@Tag(name = "Horario Tienda", description = "Gestion de horarios de tienda")

public class HorarioTiendaController {

    @Autowired
    private HorarioTiendaService horarioTiendaService;

    //Crear horario de tienda
    @Operation(summary = "Crear horario de una tienda")
    @PostMapping("/crear")
    public ResponseEntity<HorarioTiendaResponseDTO> crearHorarioTienda(@Valid @RequestBody HorarioTiendaRequestDTO horarioTienda){
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioTiendaService.crearHorarioTienda(horarioTienda));
    }

    //Listar TODOS los horarios de tienda
    @Operation(summary = "Listar todos los horarios de las tiendas")
    @GetMapping("/listar")
    public ResponseEntity<List<HorarioTiendaResponseDTO>> listarHorarioTienda(){
        return ResponseEntity.ok(horarioTiendaService.listarHorarioTienda());
    }

    //Buscar un horario de tienda por ID DE TIENDA
    @Operation(summary = "Listar un horario por ID de la tienda")
    @GetMapping("/listar/tienda/{idTienda}")
    public ResponseEntity<List<HorarioTiendaResponseDTO>> listarPorTiendaHorario(@PathVariable Long idTienda){
        return ResponseEntity.ok(horarioTiendaService.listarPorTiendaHorario(idTienda));
    }

    //Modificar horario de tienda
    @Operation(summary = "Modificar un horario")
    @PutMapping("/modificar/{idHorarioTienda}")
    public ResponseEntity<HorarioTiendaResponseDTO> modificarHorarioTienda(@PathVariable Long idHorarioTienda, @Valid @RequestBody HorarioTiendaRequestDTO horarioTienda){
        return ResponseEntity.ok(horarioTiendaService.modificarHorarioTienda(idHorarioTienda, horarioTienda));
    }

    //Desactivar horario de tienda
    @Operation(summary = "Desactivar horario de tienda")
    @PutMapping("/desactivar/{idHorarioTienda}")
    public ResponseEntity<HorarioTiendaResponseDTO> desactivarHorarioTienda(@PathVariable Long idHorarioTienda){
        return ResponseEntity.ok(horarioTiendaService.desactivarHorarioTienda(idHorarioTienda));
    }

    //Eliminar horario de tienda
    @Operation(summary = "Eliminar horario de tienda")
    @DeleteMapping("/eliminar/{idHorarioTienda}")
    public ResponseEntity<Void> eliminarHorarioTienda(@PathVariable Long idHorarioTienda){
        horarioTiendaService.eliminarHorarioTienda(idHorarioTienda);
        return ResponseEntity.noContent().build();
    }

}
