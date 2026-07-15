package com.pruebas.unitarias.envios.service;

import org.springframework.stereotype.Service;

import com.pruebas.unitarias.envios.model.Envio;
import com.pruebas.unitarias.envios.repository.EnvioRepository;

@Service
public class EnvioService {
    
    private final EnvioRepository repository;

    public EnvioService(
            EnvioRepository repository) {

        this.repository = repository;
    }

    // Crear Envío

    public Envio crearEnvio(
            Envio envio) {

        envio.setEstado("PENDIENTE");

        return repository.save(envio);
    }

    // Consultar Envío

    public Envio consultarEnvio(
            Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Envío no encontrado"));
    }

    // Actualizar Estado

    public Envio actualizarEstado(
            Long id,
            String estado) {

        Envio envio =
                consultarEnvio(id);

        envio.setEstado(estado);

        return repository.save(envio);
    }

    // Confirmar Entrega

    public Envio confirmarEntrega(
            Long id) {

        Envio envio =
                consultarEnvio(id);

        envio.setEstado("ENTREGADO");

        return repository.save(envio);
    }

    // Eliminar Envío

    public void eliminarEnvio(
            Long id) {

        repository.deleteById(id);
    }
}
