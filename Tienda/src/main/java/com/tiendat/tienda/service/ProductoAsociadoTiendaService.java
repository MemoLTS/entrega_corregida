package com.tiendat.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendat.tienda.client.CatalogoClient;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaRequestDTO;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaResponseDTO;
import com.tiendat.tienda.dto.ProductoDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.ProductoAsociadoTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.ProductoAsociadoTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional

public class ProductoAsociadoTiendaService {

    @Autowired
    private ProductoAsociadoTiendaRepository productoAsociadoTiendaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private CatalogoClient catalogoClient;

    //Crear un producto asociado a la tienda
    public ProductoAsociadoTiendaResponseDTO crearProductoAsociado(ProductoAsociadoTiendaRequestDTO request){
        Tienda tienda = buscarTienda(request.getIdTienda());

        ProductoAsociadoTienda productoAsociado = new ProductoAsociadoTienda();
        productoAsociado.setTienda(tienda);
        productoAsociado.setIdProducto(request.getIdProducto());
        productoAsociado.setNombreProducto(request.getNombreProducto());

        try {
            ProductoDTO producto = catalogoClient.obtenerProductoPorId(request.getIdProducto());
            if (producto != null) {
                productoAsociado.setNombreProducto(producto.getNombre());
            }
        } catch (Exception e) {
            log.warn("No se pudo obtener el producto {} desde Catalogo: {}", request.getIdProducto(), e.getMessage());
            if (productoAsociado.getNombreProducto() == null) {
                productoAsociado.setNombreProducto("Producto no disponible");
            }
        }
        productoAsociado.setVisibleEnTienda(true);
        ProductoAsociadoTienda creado = productoAsociadoTiendaRepository.save(productoAsociado);
        log.info("Producto asociado creado con id {}", creado.getIdProductoAsociado());
        return toResponse(creado);
    }


    //Listar todos los productos asociados a la tienda
    public List<ProductoAsociadoTiendaResponseDTO> listarTiendaProducto(){
        return productoAsociadoTiendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    //Listar productos asociados a la tienda por id de tienda
    public List<ProductoAsociadoTiendaResponseDTO> listarPorTiendaProducto(Long idTienda){
        return productoAsociadoTiendaRepository.findByTienda_IdTienda(idTienda).stream().map(this::toResponse).toList();
    }

    //Modificar producto asociado a la tienda por el id
    public ProductoAsociadoTiendaResponseDTO modificarProductoTienda(Long id, ProductoAsociadoTiendaRequestDTO request){
        ProductoAsociadoTienda existente = buscarEntidad(id);
        existente.setNombreProducto(request.getNombreProducto());
        ProductoAsociadoTienda actualizado = productoAsociadoTiendaRepository.save(existente);
        log.info("Producto asociado {} actualizado", id);
        return toResponse(actualizado);
    }

    //Ocultar un producto asociado a la tienda por el id
    public ProductoAsociadoTiendaResponseDTO ocultarProductoAsociadoTienda(Long id){
        ProductoAsociadoTienda existente = buscarEntidad(id);
        existente.setVisibleEnTienda(false);
        ProductoAsociadoTienda oculto = productoAsociadoTiendaRepository.save(existente);
        log.info("Producto asociado {} ocultado", id);
        return toResponse(oculto);
    }

    //Eliminar producto asociado a la tienda por id
    public void eliminarProductoAsociadoTienda(Long id){
        if (!productoAsociadoTiendaRepository.existsById(id)) {
            log.warn("Producto asociado con id {} no encontrado", id);
            throw new RecursoNoEncontradoException("Producto asociado con id " + id + " no encontrado");
        }
        productoAsociadoTiendaRepository.deleteById(id);
        log.info("Producto asociado {} eliminado", id);
    }

    private Tienda buscarTienda(Long idTienda){
        return tiendaRepository.findById(idTienda)
                .orElseThrow(() -> {
                    log.warn("Tienda con id {} no encontrada", idTienda);
                    return new RecursoNoEncontradoException("Tienda con id " + idTienda + " no encontrada");
                });
    }

    private ProductoAsociadoTienda buscarEntidad(Long id){
        return productoAsociadoTiendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Producto asociado con id {} no encontrado", id);
                    return new RecursoNoEncontradoException("Producto asociado con id " + id + " no encontrado");
                });
    }

    private ProductoAsociadoTiendaResponseDTO toResponse(ProductoAsociadoTienda productoAsociado){
        return new ProductoAsociadoTiendaResponseDTO(
                productoAsociado.getIdProductoAsociado(),
                productoAsociado.getTienda().getIdTienda(),
                productoAsociado.getIdProducto(),
                productoAsociado.getNombreProducto(),
                productoAsociado.isVisibleEnTienda());
    }

}
