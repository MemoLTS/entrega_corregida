package com.tiendat.tienda.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "horario_tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HorarioTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorarioTienda;

    @ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
    private Tienda tienda;

    @NotBlank
    private String diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private Boolean activo;

}
