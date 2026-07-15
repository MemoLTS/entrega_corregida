package com.tiendat.tienda.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioPersonalResponseDTO {

    private Long idHorarioPersonal;
    private Long idAsignacion;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaTermino;
    private String turno;
    private Boolean activo;
}
