package com.tiendat.tienda.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoAsociadoTiendaRequestDTO {

    @NotNull
    private Long idTienda;
    @NotNull
    private Long idProducto;
    private String nombreProducto;
}
