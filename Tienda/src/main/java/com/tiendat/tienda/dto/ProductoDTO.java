package com.tiendat.tienda.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String estado;
}
