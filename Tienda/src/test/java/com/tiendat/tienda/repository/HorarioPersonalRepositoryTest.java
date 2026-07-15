package com.tiendat.tienda.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tiendat.tienda.model.AsignacionPersonal;
import com.tiendat.tienda.model.HorarioPersonal;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.AsignacionPersonalRepository;
import com.tiendat.tienda.repository.HorarioPersonalRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HorarioPersonalRepositoryTest {

    @Autowired
    private HorarioPersonalRepository horarioPersonalRepository;

    @Autowired
    private AsignacionPersonalRepository asignacionPersonalRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    private AsignacionPersonal crearAsignacion(String nombreEmpleado){
        Tienda tienda = new Tienda();
        tienda.setNombre("Tienda Test");
        tienda = tiendaRepository.save(tienda);

        AsignacionPersonal asignacion = new AsignacionPersonal();
        asignacion.setTienda(tienda);
        asignacion.setIdEmpleado(1L);
        asignacion.setNombreEmpleado(nombreEmpleado);
        asignacion.setCargo("Vendedor");
        asignacion.setEstadoAsignacion("ACTIVA");
        return asignacionPersonalRepository.save(asignacion);
    }

    @Test
    void guardarHorarioP(){
        AsignacionPersonal asignacion = crearAsignacion("Juan Perez");

        HorarioPersonal horario = new HorarioPersonal();
        horario.setAsignacion(asignacion);
        horario.setDiaSemana("LUNES");
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraTermino(LocalTime.of(17, 0));
        horario.setTurno("NOCHE");
        horario.setActivo(true);

        HorarioPersonal guardado = horarioPersonalRepository.save(horario);

        assertNotNull(guardado.getIdHorarioPersonal());
        assertEquals("LUNES", guardado.getDiaSemana());
    }

    @Test
    void buscarIdAsignacion(){
        AsignacionPersonal asignacion1 = crearAsignacion("Juan Perez");
        AsignacionPersonal asignacion2 = crearAsignacion("Maria Lopez");

        HorarioPersonal h1 = new HorarioPersonal();
        h1.setAsignacion(asignacion1);
        h1.setDiaSemana("LUNES");
        h1.setActivo(true);

        HorarioPersonal h2 = new HorarioPersonal();
        h2.setAsignacion(asignacion1);
        h2.setDiaSemana("MARTES");
        h2.setActivo(true);

        HorarioPersonal h3 = new HorarioPersonal();
        h3.setAsignacion(asignacion2);
        h3.setDiaSemana("MIERCOLES");
        h3.setActivo(true);

        horarioPersonalRepository.save(h1);
        horarioPersonalRepository.save(h2);
        horarioPersonalRepository.save(h3);

        List<HorarioPersonal> resultado = horarioPersonalRepository.findByAsignacion_IdAsignacion(asignacion1.getIdAsignacion());

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(h -> h.getAsignacion().getIdAsignacion().equals(asignacion1.getIdAsignacion())));
    }

    @Test
    void buscarIdAsignacion_null(){
        List<HorarioPersonal> resultado = horarioPersonalRepository.findByAsignacion_IdAsignacion(99L);

        assertTrue(resultado.isEmpty());
    }

}
