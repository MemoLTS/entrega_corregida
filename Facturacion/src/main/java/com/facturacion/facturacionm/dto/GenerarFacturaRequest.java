package com.facturacion.facturacionm.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GenerarFacturaRequest {

    @NotNull(message = "El RUT del usuario es obligatorio")
    @Schema(example = "12345678")
    private Long usuarioRut;

    @Positive(message = "El ID de pedido debe ser mayor que cero")
    @Schema(example = "1")
    private int pedidoId;

    @NotEmpty(message = "La factura debe tener al menos un detalle")
    @Valid
    private List<DetalleRequest> detalles;

    @Data
    public static class DetalleRequest {

        @NotBlank(message = "La descripción es obligatoria")
        @Schema(example = "Producto Eco A")
        private String descripcion;

        @Positive(message = "La cantidad debe ser mayor que cero")
        @Schema(example = "2")
        private int cantidad;

        @Positive(message = "El precio unitario debe ser mayor que cero")
        @Schema(example = "1500.0")
        private double precioUnitario;
    }
}
