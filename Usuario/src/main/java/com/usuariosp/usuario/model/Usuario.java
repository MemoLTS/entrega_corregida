package com.usuariosp.usuario.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @NotNull(message="El rut es obligatorio")
    private Long rut;

    @Column(nullable = false)
    @NotBlank(message= "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message= "El apellido es obligatorio")
    private String apellido;

    @Column(nullable = false, unique = true)
    @NotBlank(message= "El email es obligatorio")
    @Email(message= "El formato de email es invalido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message= "El telefono es obligatorio")
    @Size(min = 8, max = 15, message = "El telefono debe tener entre 8 y 15 caracteres")
    private String telefono;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String estadoUsuario;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private CuentaUsuario cuentaUsuario;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    @JsonManagedReference
    private List<DireccionEnvio> direcciones = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    @JsonManagedReference
    private List<MetodoPago> metodosPago = new ArrayList<>();
}
