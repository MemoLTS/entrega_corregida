package com.tiendat.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaRequestDTO {

    @NotBlank
    private String nombre;
    private String codigoTienda;
    private String direccion;
    private String comuna;
    private String ciudad;
    private String region;
    private String telefono;
}
