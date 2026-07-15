package com.tiendat.tienda.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionPersonalResponseDTO {

    private Long idAsignacion;
    private Long idTienda;
    private Long idEmpleado;
    private String nombreEmpleado;
    private String cargo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
    private String estadoAsignacion;
}
