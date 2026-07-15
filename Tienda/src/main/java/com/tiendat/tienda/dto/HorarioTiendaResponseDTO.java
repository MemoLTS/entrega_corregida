package com.tiendat.tienda.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioTiendaResponseDTO {

    private Long idHorarioTienda;
    private Long idTienda;
    private String diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private Boolean activo;
}
