package com.tiendat.tienda.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionPersonalRequestDTO {

    @NotNull
    private Long idTienda;
    @NotNull
    private Long idEmpleado;
    @NotBlank
    private String nombreEmpleado;
    @NotBlank
    private String cargo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
}
