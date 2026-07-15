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

import com.tiendat.tienda.dto.AsignacionPersonalRequestDTO;
import com.tiendat.tienda.dto.AsignacionPersonalResponseDTO;
import com.tiendat.tienda.service.AsignacionPersonalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/AsignacionPersonal")
@CrossOrigin(origins = "*")
@Tag(name = "Asignacion Personal", description = "(Gestion de asignaciones de personal")

public class AsignacionPersonalController {

    @Autowired
    private AsignacionPersonalService asignacionPersonalService;

    //Crear asignacion
    @Operation(summary = "Crear una asignacion")
    @PostMapping("/crear")
    public ResponseEntity<AsignacionPersonalResponseDTO> crearAsignacionP(@Valid @RequestBody AsignacionPersonalRequestDTO asignacionPersonal){
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionPersonalService.crearAsignacionP(asignacionPersonal));
    }

    //Listar todas las asignaciones
    @Operation(summary = "Listar todas las asignaciones del personal")
    @GetMapping("/listar")
    public ResponseEntity<List<AsignacionPersonalResponseDTO>> listarAsignacionPersonal(){
        return ResponseEntity.ok(asignacionPersonalService.listarAsignacionPersonal());
    }

    //Buscar una asignacion por id de tienda
    @Operation(summary = "Buscar una asignacion por id de la tienda")
    @GetMapping("/listar/tienda/{idTienda}")
    public ResponseEntity<List<AsignacionPersonalResponseDTO>> listarPorTiendaAsignacionP(@PathVariable Long idTienda){
        return ResponseEntity.ok(asignacionPersonalService.listarPorTiendaAsignacionP(idTienda));
    }

    //Modificar una asignacion por id de la asignacion
    @Operation(summary = ("Modificar una asignacion"))
    @PutMapping("/modificar/{idAsignacion}")
    public ResponseEntity<AsignacionPersonalResponseDTO> modificarAsignacionP(@PathVariable Long idAsignacion, @Valid @RequestBody AsignacionPersonalRequestDTO asignacionPersonal){
        return ResponseEntity.ok(asignacionPersonalService.modificarAsignacionP(idAsignacion, asignacionPersonal));
    }

    //Desactivar una asignacion por su id
    @Operation(summary = "Desactivar una asignacion")
    @PutMapping("/desactivar/{idAsignacion}")
    public ResponseEntity<AsignacionPersonalResponseDTO> desactivarAsignacionP(@PathVariable Long idAsignacion){
        return ResponseEntity.ok(asignacionPersonalService.desactivarAsignacionP(idAsignacion));
    }

    //Eliminar asignacion
    @Operation(summary = "Eliminar una asignacion")
    @DeleteMapping("/eliminar/{idAsignacion}")
    public ResponseEntity<Void> eliminarAsignacionP(@PathVariable Long idAsignacion){
        asignacionPersonalService.eliminarAsignacionP(idAsignacion);
        return ResponseEntity.noContent().build();
    }
}
