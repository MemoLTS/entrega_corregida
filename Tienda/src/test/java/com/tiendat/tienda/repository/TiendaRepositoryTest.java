package com.tiendat.tienda.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.TiendaRepository;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TiendaRepositoryTest {

    @Autowired
    private TiendaRepository tiendaRepository;

    @Test
    void guardar_asignarId(){
        Tienda tienda = new Tienda();
        tienda.setNombre("EcoMarket Santiago centro");
        tienda.setEstado("ACTIVA");

        Tienda guardada = tiendaRepository.save(tienda);

        assertNotNull(guardada.getIdTienda());
        assertEquals("EcoMarket Santiago centro", guardada.getNombre());
    }

    @Test
    void buscarTienda() { //Buscar por id
        Tienda tienda  = new Tienda();
        tienda.setNombre("EcoMarket calama");
        tienda.setEstado("ACTIVA");
        Tienda guardada = tiendaRepository.save(tienda);

        Optional<Tienda> resultado = tiendaRepository.findById(guardada.getIdTienda());

        assertTrue(resultado.isPresent());
        assertEquals("EcoMarket calama", resultado.get().getNombre());
    }

    @Test
    void listarTodasLasTiendas(){
        Tienda t1 = new Tienda();
        t1.setNombre("Tienda A");
        t1.setEstado("ACTIVA");
        Tienda t2 = new Tienda();
        t2.setNombre("Tienda B");
        t2.setEstado("ACTIVA");

        tiendaRepository.save(t1);
        tiendaRepository.save(t2);

        List<Tienda> resultado = tiendaRepository.findAll();

        assertEquals(2, resultado.size());
    }

    @Test 
    void borrarTienda(){
        Tienda tienda = new Tienda();
        tienda.setNombre("Tienda temporal");
        tienda.setEstado("ACTIVA");
        Tienda guardada = tiendaRepository.save(tienda);

        tiendaRepository.deleteById(guardada.getIdTienda());

        Optional<Tienda> resultado = tiendaRepository.findById(guardada.getIdTienda());
        assertFalse(resultado.isPresent());
    }
    
}
