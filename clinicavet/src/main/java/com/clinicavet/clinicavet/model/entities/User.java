package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, length = 25)
    private String name;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "contraseña", nullable = false, length = 255)
    private String password;

    @Column(name = "activo", nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Role role;
}