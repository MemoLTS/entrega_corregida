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
public class HorarioTiendaRequestDTO {

    @NotNull
    private Long idTienda;
    @NotBlank
    private String diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
}
