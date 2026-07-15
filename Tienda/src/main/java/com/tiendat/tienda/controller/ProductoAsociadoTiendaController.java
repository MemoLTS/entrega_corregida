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

import com.tiendat.tienda.dto.ProductoAsociadoTiendaRequestDTO;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaResponseDTO;
import com.tiendat.tienda.service.ProductoAsociadoTiendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/ProductoAsociadoTienda")
@CrossOrigin(origins = "*")
@Tag(name = "Producto asociado a la tienda", description = "Gestion de los productos asociados a la tienda")

public class ProductoAsociadoTiendaController {

    @Autowired
    private ProductoAsociadoTiendaService productoAsociadoTiendaService;

    //Crear producto asociado
    @Operation(summary = "Crear un producto asociado")
    @PostMapping("/crear")
    public ResponseEntity<ProductoAsociadoTiendaResponseDTO> crearProductoAsociado (@Valid @RequestBody ProductoAsociadoTiendaRequestDTO productoAsociadoTienda){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoAsociadoTiendaService.crearProductoAsociado(productoAsociadoTienda));
    }

    //Listar todos los produtos asociados por el id de la tienda
    @Operation(summary = "Listar todos los productos asociado a esa tienda")
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoAsociadoTiendaResponseDTO>> listarTiendaProducto(){
        return ResponseEntity.ok(productoAsociadoTiendaService.listarTiendaProducto());
    }

    //Buscar producto asociado por idTienda
    @Operation(summary = "Buscar un producto por tienda")
    @GetMapping("/listar/tienda/{idTienda}")
    public ResponseEntity<List<ProductoAsociadoTiendaResponseDTO>> listarPorTiendaProducto(@PathVariable Long idTienda){
        return ResponseEntity.ok(productoAsociadoTiendaService.listarPorTiendaProducto(idTienda));
    }

    //Modificar producto asociado
    @Operation(summary = "Modificar un producto")
    @PutMapping("/modificar/{idProductoAsociado}")
    public ResponseEntity<ProductoAsociadoTiendaResponseDTO> modificarProductoTienda(@PathVariable Long idProductoAsociado, @Valid @RequestBody ProductoAsociadoTiendaRequestDTO productoAsociadoTienda){
        return ResponseEntity.ok(productoAsociadoTiendaService.modificarProductoTienda(idProductoAsociado, productoAsociadoTienda));
    }

    //Ocultar producto
    @Operation(summary = "Ocultar un prodcuto asociado")
    @PutMapping("/ocultar/{idProductoAsociado}")
    public ResponseEntity<ProductoAsociadoTiendaResponseDTO> ocultarProductoAsociadoTienda(@PathVariable Long idProductoAsociado){
        return ResponseEntity.ok(productoAsociadoTiendaService.ocultarProductoAsociadoTienda(idProductoAsociado));
    }

    //Eliminar producto asociado
    @Operation(summary = "Eliminar un producto asociado")
    @DeleteMapping("/eliminar/{idProductoAsociado}")
    public ResponseEntity<Void> eliminarProductoAsociadoTienda(@PathVariable Long idProductoAsociado){
        productoAsociadoTiendaService.eliminarProductoAsociadoTienda(idProductoAsociado);
        return ResponseEntity.noContent().build();
    }

}
