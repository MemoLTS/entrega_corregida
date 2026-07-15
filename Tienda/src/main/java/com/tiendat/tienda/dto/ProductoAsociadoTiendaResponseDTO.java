package com.tiendat.tienda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoAsociadoTiendaResponseDTO {

    private Long idProductoAsociado;
    private Long idTienda;
    private Long idProducto;
    private String nombreProducto;
    private boolean visibleEnTienda;
}
