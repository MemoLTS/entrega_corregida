package com.tiendat.tienda.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTiendaResponseDTO {

    private Long idReporte;
    private Long idTienda;
    private String tipoReporte;
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFin;
    private LocalDateTime fechaGeneracion;
}
