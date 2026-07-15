package com.tiendat.tienda.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tiendat.tienda.model.ReporteTienda;
import com.tiendat.tienda.model.Tienda;
import com.tiendat.tienda.repository.ReporteTiendaRepository;
import com.tiendat.tienda.repository.TiendaRepository;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReporteTiendaRepositoryTest {

    @Autowired
    private ReporteTiendaRepository reporteTiendaRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    private Tienda crearTienda(String nombre){
        Tienda tienda = new Tienda();
        tienda.setNombre(nombre);
        return tiendaRepository.save(tienda);
    }

    @Test
    void guardarReporteT(){
        Tienda tienda = crearTienda("Tienda Central");

        ReporteTienda reporte = new ReporteTienda();
        reporte.setTienda(tienda);
        reporte.setTipoReporte("VENTAS");
        reporte.setFechaGeneracion(LocalDateTime.now());

        ReporteTienda guardado = reporteTiendaRepository.save(reporte);

        assertNotNull(guardado.getIdReporte());
        assertEquals("VENTAS", guardado.getTipoReporte());
    }

    @Test
    void buscarReporteIdTienda(){
        Tienda tienda1 = crearTienda("Tienda Uno");
        Tienda tienda2 = crearTienda("Tienda Dos");

        ReporteTienda r1 = new ReporteTienda();
        r1.setTienda(tienda1);
        r1.setTipoReporte("VENTAS");
        r1.setFechaGeneracion(LocalDateTime.now());

        ReporteTienda r2 = new ReporteTienda();
        r2.setTienda(tienda1);
        r2.setTipoReporte("INVENTARIO");
        r2.setFechaGeneracion(LocalDateTime.now());

        ReporteTienda r3 = new ReporteTienda();
        r3.setTienda(tienda2);
        r3.setTipoReporte("VENTAS");
        r3.setFechaGeneracion(LocalDateTime.now());

        reporteTiendaRepository.save(r1);
        reporteTiendaRepository.save(r2);
        reporteTiendaRepository.save(r3);

        List<ReporteTienda> resultado = reporteTiendaRepository.findByTienda_IdTienda(tienda1.getIdTienda());

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(r -> r.getTienda().getIdTienda().equals(tienda1.getIdTienda())));
    }

    @Test
    void buscarReporteIdTienda_null(){
        List<ReporteTienda> resultado = reporteTiendaRepository.findByTienda_IdTienda(99L);

        assertTrue(resultado.isEmpty());
    }

}
