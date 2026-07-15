package com.tiendat.tienda.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioPersonalRequestDTO {

    @NotNull
    private Long idAsignacion;
    @NotBlank
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaTermino;
    private String turno;
}
