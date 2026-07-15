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
public class ReporteTiendaRequestDTO {

    @NotNull
    private Long idTienda;
    @NotBlank
    private String tipoReporte;
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFin;
}
