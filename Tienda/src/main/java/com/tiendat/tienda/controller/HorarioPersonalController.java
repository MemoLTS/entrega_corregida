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

import com.tiendat.tienda.dto.HorarioPersonalRequestDTO;
import com.tiendat.tienda.dto.HorarioPersonalResponseDTO;
import com.tiendat.tienda.service.HorarioPersonalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/HorarioPersonal")
@CrossOrigin(origins = "*")
@Tag(name = "Horario personal", description = "Gestion de los horarios del personal")

public class HorarioPersonalController {

    @Autowired
    private HorarioPersonalService horarioPersonalService;

    //Crear horario del personal
    @Operation(summary = "Crear un horario del personal")
    @PostMapping("/crear")
    public ResponseEntity<HorarioPersonalResponseDTO> crear(@Valid @RequestBody HorarioPersonalRequestDTO horarioPersonal){
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioPersonalService.crear(horarioPersonal));
    }

    //Listar todos los horarios del personal
    @Operation(summary = "Listar todos los horarios del personal")
    @GetMapping("/listar")
    public ResponseEntity<List<HorarioPersonalResponseDTO>> listar(){
        return ResponseEntity.ok(horarioPersonalService.listar());
    }

    //Listar horarios del personal por id de asignacion
    @Operation(summary = "Buscar un horario por el id de la asignacion")
    @GetMapping("/listar/asignacion/{idAsignacion}")
    public ResponseEntity<List<HorarioPersonalResponseDTO>> listarPorAsignacion(@PathVariable Long idAsignacion){
        return ResponseEntity.ok(horarioPersonalService.listarPorAsignacion(idAsignacion));
    }

    //Modificar horario del personal por id
    @Operation(summary = "Modificar un horario")
    @PutMapping("/modificar/{idHorarioPersonal}")
    public ResponseEntity<HorarioPersonalResponseDTO> modificar(@PathVariable Long idHorarioPersonal, @Valid @RequestBody HorarioPersonalRequestDTO horarioPersonal){
        return ResponseEntity.ok(horarioPersonalService.modificar(idHorarioPersonal, horarioPersonal));
    }

    //Desactivar horario del personal por id
    @Operation(summary = "Desactivar un horario del personal")
    @PutMapping("/desactivar/{idHorarioPersonal}")
    public ResponseEntity<HorarioPersonalResponseDTO> desactivar(@PathVariable Long idHorarioPersonal){
        return ResponseEntity.ok(horarioPersonalService.desactivar(idHorarioPersonal));
    }

    //Eliminar horario del personal por id
    @Operation(summary = "Eliminar un horario del personal")
    @DeleteMapping("/eliminar/{idHorarioPersonal}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idHorarioPersonal){
        horarioPersonalService.eliminar(idHorarioPersonal);
        return ResponseEntity.noContent().build();
    }

}
