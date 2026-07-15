package com.pruebas.unitarias.rutayseguimiento.service;

import org.springframework.stereotype.Service;

import com.pruebas.unitarias.rutayseguimiento.model.Ruta;
import com.pruebas.unitarias.rutayseguimiento.repository.RutaRepository;

@Service
public class RutaService {

    private final RutaRepository repository;

    public RutaService(
            RutaRepository repository) {

        this.repository = repository;
    }

    // Crear Ruta

    public Ruta crearRuta(
            Ruta ruta) {

        ruta.setEstado("EN_RUTA");

        return repository.save(ruta);
    }

    // Consultar Ruta

    public Ruta consultarRuta(
            Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ruta no encontrada"));
    }

    // Actualizar Ubicación

    public Ruta actualizarUbicacion(
            Long id,
            String ubicacion) {

        Ruta ruta =
                consultarRuta(id);

        ruta.setUbicacionActual(
                ubicacion);

        return repository.save(ruta);
    }

    // Registrar Seguimiento

    public Ruta registrarSeguimiento(
            Long id,
            String estado) {

        Ruta ruta =
                consultarRuta(id);

        ruta.setEstado(estado);

        return repository.save(ruta);
    }

    // Finalizar Ruta

    public Ruta finalizarRuta(
            Long id) {

        Ruta ruta =
                consultarRuta(id);

        ruta.setEstado("FINALIZADA");

        return repository.save(ruta);
    }

    // Eliminar Ruta

    public void eliminarRuta(
            Long id) {

        repository.deleteById(id);
    }
}
