package com.tiendat.tienda.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tiendat.tienda.model.AsignacionPersonal;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.AsignacionPersonalRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
@DataJpaTest
class AsignacionPersonalRepositoryTest {

    @Autowired
    private AsignacionPersonalRepository asignacionPersonalRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    private Tienda crearTienda(String nombre){
        Tienda tienda = new Tienda();
        tienda.setNombre(nombre);
        return tiendaRepository.save(tienda);
    }

    @Test
    void guardarAsignacion(){
        Tienda tienda = crearTienda("Tienda Central");

        AsignacionPersonal asignacion = new AsignacionPersonal();
        asignacion.setTienda(tienda);
        asignacion.setIdEmpleado(1L);
        asignacion.setNombreEmpleado("Juan Perez");
        asignacion.setCargo("Vendedor");
        asignacion.setEstadoAsignacion("ACTIVA");

        AsignacionPersonal guardada = asignacionPersonalRepository.save(asignacion);

        assertNotNull(guardada.getIdAsignacion());
        assertEquals("Juan Perez", guardada.getNombreEmpleado());
    }

    @Test
    void buscarTiendaId(){
        Tienda tienda1 = crearTienda("Tienda Uno");
        Tienda tienda2 = crearTienda("Tienda Dos");

        AsignacionPersonal a1 = new AsignacionPersonal();
        a1.setTienda(tienda1);
        a1.setIdEmpleado(1L);
        a1.setNombreEmpleado("Juan Perez");
        a1.setCargo("Vendedor");
        a1.setEstadoAsignacion("ACTIVA");

        AsignacionPersonal a2 = new AsignacionPersonal();
        a2.setTienda(tienda1);
        a2.setIdEmpleado(2L);
        a2.setNombreEmpleado("Maria Lopez");
        a2.setCargo("Cajera");
        a2.setEstadoAsignacion("ACTIVA");

        AsignacionPersonal a3 = new AsignacionPersonal();
        a3.setTienda(tienda2);
        a3.setIdEmpleado(3L);
        a3.setNombreEmpleado("Pedro Soto");
        a3.setCargo("Almacenero");
        a3.setEstadoAsignacion("ACTIVA");

        asignacionPersonalRepository.save(a1);
        asignacionPersonalRepository.save(a2);
        asignacionPersonalRepository.save(a3);

        List<AsignacionPersonal> resultado = asignacionPersonalRepository.findByTienda_IdTienda(tienda1.getIdTienda());

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(a -> a.getTienda().getIdTienda().equals(tienda1.getIdTienda())));
    }

    @Test
    void buscarTiendaId_Null(){
        List<AsignacionPersonal> resultado = asignacionPersonalRepository.findByTienda_IdTienda(99L);

        assertTrue(resultado.isEmpty());
    }

}
