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
@Table(name = "horario_personal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class HorarioPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorarioPersonal;

    @ManyToOne
    @JoinColumn(name = "id_asignacion", nullable = false)
    private AsignacionPersonal asignacion;

    @NotBlank
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaTermino;
    private String turno;
    private Boolean activo;

}
