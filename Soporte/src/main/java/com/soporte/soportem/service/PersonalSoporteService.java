package com.soporte.soportem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soporte.soportem.dto.PersonalSoporteDTO;
import com.soporte.soportem.exception.BusinessException;
import com.soporte.soportem.exception.ResourceNotFoundException;
import com.soporte.soportem.model.PersonalSoporte;
import com.soporte.soportem.repository.PersonalSoporteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor

public class PersonalSoporteService {

    private final PersonalSoporteRepository personalSoporteRepository;

    public List<PersonalSoporte> listarTodos() {
        return personalSoporteRepository.findAll();
    }

    public PersonalSoporte obtenerPorIdPersonal(Long idPersonal) {
        return personalSoporteRepository.findById(idPersonal)
            .orElseThrow(() -> new ResourceNotFoundException("Personal de soporte", idPersonal));
    }

    public PersonalSoporte crearPersonal(PersonalSoporteDTO dto) {
        log.info("Creando personal de soporte: {}", dto.getRut());

        if (personalSoporteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("Ya existe personal registrado con ese email");
        }

        PersonalSoporte personal = PersonalSoporte.builder()
            .rutPersonalS(dto.getRut())
            .nombre(dto.getNombre())
            .apellido(dto.getApellido())
            .email(dto.getEmail())
            .rol(dto.getRol())
            .estado(dto.getEstado())
            .build();

        return personalSoporteRepository.save(personal);
    }

    public PersonalSoporte actualizarPersonal(Long idPersonal, PersonalSoporteDTO dto) {
        PersonalSoporte personal = obtenerPorIdPersonal(idPersonal);
        personal.setNombre(dto.getNombre());
        personal.setApellido(dto.getApellido());
        personal.setEmail(dto.getEmail());
        personal.setRol(dto.getRol());
        personal.setEstado(dto.getEstado());
        return personalSoporteRepository.save(personal);
    }

    public void eliminarPersonal(Long idPersonal) {
        if (!personalSoporteRepository.existsById(idPersonal)) {
            throw new ResourceNotFoundException("Personal de soporte", idPersonal);
        }
        personalSoporteRepository.deleteById(idPersonal);
    }

}
