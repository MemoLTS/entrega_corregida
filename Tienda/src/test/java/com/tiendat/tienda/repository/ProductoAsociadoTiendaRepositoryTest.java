package com.tiendat.tienda.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tiendat.tienda.model.ProductoAsociadoTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.ProductoAsociadoTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductoAsociadoTiendaRepositoryTest {

    @Autowired
    private ProductoAsociadoTiendaRepository productoAsociadoTiendaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    private Tienda crearTienda(String nombre){
        Tienda tienda = new Tienda();
        tienda.setNombre(nombre);
        return tiendaRepository.save(tienda);
    }

    @Test
    void guardarProductoAsociadoT(){
        Tienda tienda = crearTienda("Tienda Central");

        ProductoAsociadoTienda producto = new ProductoAsociadoTienda();
        producto.setTienda(tienda);
        producto.setIdProducto(10L);
        producto.setNombreProducto("Jugo de naranja");
        producto.setVisibleEnTienda(true);

        ProductoAsociadoTienda guardado = productoAsociadoTiendaRepository.save(producto);

        assertNotNull(guardado.getIdProductoAsociado());
        assertEquals("Jugo de naranja", guardado.getNombreProducto());
    }

    @Test
    void buscarProductoTienda(){
        Tienda tienda1 = crearTienda("Tienda Uno");
        Tienda tienda2 = crearTienda("Tienda Dos");

        ProductoAsociadoTienda p1 = new ProductoAsociadoTienda();
        p1.setTienda(tienda1);
        p1.setIdProducto(1L);
        p1.setNombreProducto("Manzana Organica");
        p1.setVisibleEnTienda(true);

        ProductoAsociadoTienda p2 = new ProductoAsociadoTienda();
        p2.setTienda(tienda1);
        p2.setIdProducto(2L);
        p2.setNombreProducto("Pera Organica");
        p2.setVisibleEnTienda(true);

        ProductoAsociadoTienda p3 = new ProductoAsociadoTienda();
        p3.setTienda(tienda2);
        p3.setIdProducto(3L);
        p3.setNombreProducto("Naranja Organica");
        p3.setVisibleEnTienda(true);

        productoAsociadoTiendaRepository.save(p1);
        productoAsociadoTiendaRepository.save(p2);
        productoAsociadoTiendaRepository.save(p3);

        List<ProductoAsociadoTienda> resultado = productoAsociadoTiendaRepository.findByTienda_IdTienda(tienda1.getIdTienda());

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getTienda().getIdTienda().equals(tienda1.getIdTienda())));
    }

    @Test
    void buscarProductoIdTienda(){
        List<ProductoAsociadoTienda> resultado = productoAsociadoTiendaRepository.findByTienda_IdTienda(99L);

        assertTrue(resultado.isEmpty());
    }

}
