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

import com.tiendat.tienda.dto.TiendaRequestDTO;
import com.tiendat.tienda.dto.TiendaResponseDTO;
import com.tiendat.tienda.service.TiendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/tienda")
@CrossOrigin(origins = "*")
@Tag(name = "Tienda", description = "Gestion de tienda")

public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    //Crear una nueva tienda
    @Operation(summary = "Crear una nueva tienda")
    @PostMapping("/crear")
    public ResponseEntity<TiendaResponseDTO> crearTienda(@Valid @RequestBody TiendaRequestDTO tienda){
        return ResponseEntity.status(HttpStatus.CREATED).body(tiendaService.crearTienda(tienda));
    }

    //Listar las tiendas
    @Operation(summary = "Listar todas las tiendas")
    @GetMapping("/listar")
    public ResponseEntity<List<TiendaResponseDTO>> listarTiendas(){
        return ResponseEntity.ok(tiendaService.listarTiendas());
    }

    //Buscar tienda por id
    @Operation(summary = "Buscar una tienda")
    @GetMapping("/buscar/{idTienda}")
    public ResponseEntity<TiendaResponseDTO> buscarTiendaPorId(@PathVariable Long idTienda){
        return ResponseEntity.ok(tiendaService.buscarTiendaPorId(idTienda));
    }

    //Modificar tienda
    @Operation(summary = "Modificar una tienda por su id")
    @PutMapping("/modificar/{idTienda}")
    public ResponseEntity<TiendaResponseDTO> modificarTienda(@PathVariable Long idTienda, @Valid @RequestBody TiendaRequestDTO tienda){
        return ResponseEntity.ok(tiendaService.modificarTienda(idTienda, tienda));
    }

    //Desactivar tienda
    @Operation(summary = "Desactivar una tienda")
    @PutMapping("/desactivar/{idTienda}")
    public ResponseEntity<TiendaResponseDTO> desactivarTienda(@PathVariable Long idTienda){
        return ResponseEntity.ok(tiendaService.desactivarTienda(idTienda));
    }

    //Eliminar tienda
    @Operation(summary = "Eliminar una tienda")
    @DeleteMapping("/eliminar/{idTienda}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Long idTienda){
        tiendaService.eliminarTienda(idTienda);
        return ResponseEntity.noContent().build();
    }

}
