package com.tiendat.tienda.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiendat.tienda.client.CatalogoClient;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaRequestDTO;
import com.tiendat.tienda.dto.ProductoAsociadoTiendaResponseDTO;
import com.tiendat.tienda.dto.ProductoDTO;
import com.tiendat.tienda.exception.RecursoNoEncontradoException;
import com.tiendat.tienda.model.ProductoAsociadoTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.ProductoAsociadoTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;
import com.tiendat.tienda.service.ProductoAsociadoTiendaService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductoAsociadoTiendaServiceTest {

    @Mock
    private ProductoAsociadoTiendaRepository productoAsociadoTiendaRepository;

    @Mock
    private TiendaRepository tiendaRepository;

    @Mock
    private CatalogoClient catalogoClient;

    @InjectMocks
    private ProductoAsociadoTiendaService productoAsociadoTiendaService;

    private Tienda tiendaExistente(Long id){
        Tienda tienda = new Tienda();
        tienda.setIdTienda(id);
        return tienda;
    }

    @Test
    void crearProductoAsociad(){
        ProductoAsociadoTiendaRequestDTO request = new ProductoAsociadoTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setIdProducto(10L);

        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(10L);
        dto.setNombre("Manzana Roja");
        dto.setDescripcion("Fruta");
        dto.setPrecio(100.0);
        dto.setEstado("ACTIVO");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(catalogoClient.obtenerProductoPorId(10L)).thenReturn(dto);
        when(productoAsociadoTiendaRepository.save(any(ProductoAsociadoTienda.class)))
            .thenAnswer(inv -> inv.getArgument(0, ProductoAsociadoTienda.class));

        ProductoAsociadoTiendaResponseDTO resultado = productoAsociadoTiendaService.crearProductoAsociado(request);

        assertEquals("Manzana Roja", resultado.getNombreProducto());
        assertTrue(resultado.isVisibleEnTienda());
        verify(productoAsociadoTiendaRepository, times(1)).save(any(ProductoAsociadoTienda.class));
    }

    @Test
    void crearProductoAsociado_debeLanzarExcepcionCuandoTiendaNoExiste(){
        ProductoAsociadoTiendaRequestDTO request = new ProductoAsociadoTiendaRequestDTO();
        request.setIdTienda(99L);
        request.setIdProducto(10L);

        when(tiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> productoAsociadoTiendaService.crearProductoAsociado(request));
        verify(productoAsociadoTiendaRepository, never()).save(any());
    }

    @Test
    void crearProductoAsociado_productoNull(){
        ProductoAsociadoTiendaRequestDTO request = new ProductoAsociadoTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setIdProducto(1L);
        request.setNombreProducto("Nombre manual");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(productoAsociadoTiendaRepository.save(any(ProductoAsociadoTienda.class)))
            .thenAnswer(inv -> inv.getArgument(0, ProductoAsociadoTienda.class));

        ProductoAsociadoTiendaResponseDTO resultado = productoAsociadoTiendaService.crearProductoAsociado(request);

        assertEquals("Nombre manual", resultado.getNombreProducto());
        assertTrue(resultado.isVisibleEnTienda());
    }

    @Test
    void crearProductoAsociado_excepcionNombreNull(){
        ProductoAsociadoTiendaRequestDTO request = new ProductoAsociadoTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setIdProducto(1L);

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(catalogoClient.obtenerProductoPorId(1L)).thenThrow(new RuntimeException("Error"));
        when(productoAsociadoTiendaRepository.save(any(ProductoAsociadoTienda.class)))
            .thenAnswer(inv -> inv.getArgument(0, ProductoAsociadoTienda.class));

        ProductoAsociadoTiendaResponseDTO resultado = productoAsociadoTiendaService.crearProductoAsociado(request);

        assertEquals("Producto no disponible", resultado.getNombreProducto());
        assertTrue(resultado.isVisibleEnTienda());
    }

    @Test
    void crearProductoAsociado_excepcionConNombre(){
        ProductoAsociadoTiendaRequestDTO request = new ProductoAsociadoTiendaRequestDTO();
        request.setIdTienda(1L);
        request.setIdProducto(1L);
        request.setNombreProducto("Nombre existente");

        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaExistente(1L)));
        when(catalogoClient.obtenerProductoPorId(1L)).thenThrow(new RuntimeException("Error"));
        when(productoAsociadoTiendaRepository.save(any(ProductoAsociadoTienda.class)))
            .thenAnswer(inv -> inv.getArgument(0, ProductoAsociadoTienda.class));

        ProductoAsociadoTiendaResponseDTO resultado = productoAsociadoTiendaService.crearProductoAsociado(request);

        assertEquals("Nombre existente", resultado.getNombreProducto());
        assertTrue(resultado.isVisibleEnTienda());
    }

    @Test
    void listarTiendaProducto(){ //Mostrar todos los productos
        ProductoAsociadoTienda p1 = new ProductoAsociadoTienda();
        p1.setTienda(tiendaExistente(1L));
        ProductoAsociadoTienda p2 = new ProductoAsociadoTienda();
        p2.setTienda(tiendaExistente(1L));

        when(productoAsociadoTiendaRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductoAsociadoTiendaResponseDTO> resultado = productoAsociadoTiendaService.listarTiendaProducto();

        assertEquals(2, resultado.size());
        verify(productoAsociadoTiendaRepository, times(1)).findAll();
    }

    @Test
    void listarPorTiendaProducto(){
        ProductoAsociadoTienda p1 = new ProductoAsociadoTienda();
        p1.setTienda(tiendaExistente(1L));

        when(productoAsociadoTiendaRepository.findByTienda_IdTienda(1L)).thenReturn(List.of(p1));

        List<ProductoAsociadoTiendaResponseDTO> resultado = productoAsociadoTiendaService.listarPorTiendaProducto(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdTienda());
    }

    @Test
    void modificarProductoTienda(){ //Cuando existe
        ProductoAsociadoTienda existente = new ProductoAsociadoTienda();
        existente.setIdProductoAsociado(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setNombreProducto("Nombre viejo");
        existente.setVisibleEnTienda(true);

        ProductoAsociadoTiendaRequestDTO datos = new ProductoAsociadoTiendaRequestDTO();
        datos.setIdTienda(1L);
        datos.setIdProducto(1L);
        datos.setNombreProducto("Nombre nuevo");

        when(productoAsociadoTiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoAsociadoTiendaRepository.save(any(ProductoAsociadoTienda.class)))
            .thenAnswer(inv -> inv.getArgument(0, ProductoAsociadoTienda.class));

        ProductoAsociadoTiendaResponseDTO resultado = productoAsociadoTiendaService.modificarProductoTienda(1L, datos);

        assertEquals("Nombre nuevo", resultado.getNombreProducto());
    }

    @Test
    void modificarProductoTienda_debeLanzarExcepcionCuandoNoExiste(){ //Cuando no existe
        when(productoAsociadoTiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> productoAsociadoTiendaService.modificarProductoTienda(99L, new ProductoAsociadoTiendaRequestDTO()));
        verify(productoAsociadoTiendaRepository, never()).save(any());
    }

    @Test
    void ocultarProductoAsociadoTienda(){ //Cuando existe y cambiamos el visibleEnTienda a false
        ProductoAsociadoTienda existente = new ProductoAsociadoTienda();
        existente.setIdProductoAsociado(1L);
        existente.setTienda(tiendaExistente(1L));
        existente.setVisibleEnTienda(true);

        when(productoAsociadoTiendaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoAsociadoTiendaRepository.save(any(ProductoAsociadoTienda.class)))
            .thenAnswer(inv -> inv.getArgument(0, ProductoAsociadoTienda.class));

        ProductoAsociadoTiendaResponseDTO resultado = productoAsociadoTiendaService.ocultarProductoAsociadoTienda(1L);

        assertFalse(resultado.isVisibleEnTienda());
    }

    @Test
    void ocultarProductoAsociadoTienda_debeLanzarExcepcionCuandoNoExiste(){ //Cuando no existe
        when(productoAsociadoTiendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> productoAsociadoTiendaService.ocultarProductoAsociadoTienda(99L));
    }

    @Test
    void eliminarProductoAsociadoTienda(){
        when(productoAsociadoTiendaRepository.existsById(1L)).thenReturn(true);

        productoAsociadoTiendaService.eliminarProductoAsociadoTienda(1L);

        verify(productoAsociadoTiendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarProductoAsociadoTienda_debeLanzarExcepcionCuandoNoExiste(){
        when(productoAsociadoTiendaRepository.existsById(99L)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> productoAsociadoTiendaService.eliminarProductoAsociadoTienda(99L));
        verify(productoAsociadoTiendaRepository, never()).deleteById(any());
    }
}
