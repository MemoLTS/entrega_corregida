package com.tiendat.tienda.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tienda")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTienda;

    @NotBlank
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
