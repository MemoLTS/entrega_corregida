package com.tiendat.tienda.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tiendat.tienda.model.HorarioTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.HorarioTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HorarioTiendaRepositoryTest {

    @Autowired
    private HorarioTiendaRepository horarioTiendaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    private Tienda crearTienda(String nombre){
        Tienda tienda = new Tienda();
        tienda.setNombre(nombre);
        return tiendaRepository.save(tienda);
    }

    @Test
    void guardarHorarioT(){
        Tienda tienda = crearTienda("Tienda Central");

        HorarioTienda horario = new HorarioTienda();
        horario.setTienda(tienda);
        horario.setDiaSemana("LUNES");
        horario.setHoraApertura(LocalTime.of(9, 0));
        horario.setHoraCierre(LocalTime.of(18, 0));
        horario.setActivo(true);

        HorarioTienda guardado = horarioTiendaRepository.save(horario);

        assertNotNull(guardado.getIdHorarioTienda());
        assertEquals("LUNES", guardado.getDiaSemana());
    }

    @Test
    void buscarTiendaId(){
        Tienda tienda1 = crearTienda("Tienda Uno");
        Tienda tienda2 = crearTienda("Tienda Dos");

        HorarioTienda h1 = new HorarioTienda();
        h1.setTienda(tienda1);
        h1.setDiaSemana("LUNES");
        h1.setActivo(true);

        HorarioTienda h2 = new HorarioTienda();
        h2.setTienda(tienda1);
        h2.setDiaSemana("MARTES");
        h2.setActivo(true);

        HorarioTienda h3 = new HorarioTienda();
        h3.setTienda(tienda2);
        h3.setDiaSemana("MIERCOLES");
        h3.setActivo(true);

        horarioTiendaRepository.save(h1);
        horarioTiendaRepository.save(h2);
        horarioTiendaRepository.save(h3);

        List<HorarioTienda> resultado = horarioTiendaRepository.findByTienda_IdTienda(tienda1.getIdTienda());

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(h -> h.getTienda().getIdTienda().equals(tienda1.getIdTienda())));
    }

    @Test
    void buscarTiendaId_null(){ //Cuando la tienda no existe
        List<HorarioTienda> resultado = horarioTiendaRepository.findByTienda_IdTienda(99L);

        assertTrue(resultado.isEmpty());
    }

}
