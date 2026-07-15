package com.tiendat.tienda.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asignacion_personal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AsignacionPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @NotNull
    private Long idEmpleado;
    @NotBlank
    private String nombreEmpleado;
    @NotBlank
    private String cargo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaTermino;
    private String estadoAsignacion;

}
