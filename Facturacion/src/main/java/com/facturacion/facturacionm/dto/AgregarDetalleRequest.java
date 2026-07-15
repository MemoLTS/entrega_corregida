package com.facturacion.facturacionm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AgregarDetalleRequest {

    @NotBlank(message = "La descripción es obligatoria")
    @Schema(example = "Producto Extra")
    private String descripcion;

    @Positive(message = "La cantidad debe ser mayor que cero")
    @Schema(example = "3")
    private int cantidad;

    @Positive(message = "El precio unitario debe ser mayor que cero")
    @Schema(example = "800.0")
    private double precioUnitario;
}
