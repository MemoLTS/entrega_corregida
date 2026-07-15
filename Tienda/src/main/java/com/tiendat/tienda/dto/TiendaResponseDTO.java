package com.tiendat.tienda.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaResponseDTO {

    private Long idTienda;
    private String nombre;
    private String codigoTienda;
    private String direccion;
    private String comuna;
    private String ciudad;
    private String region;
    private String telefono;
    private String estado;
    private LocalDateTime fechaCreacion;
}
