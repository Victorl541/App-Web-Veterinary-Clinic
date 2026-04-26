package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "duenios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cedula", nullable = false, length = 20)
    private String cedula;

    @Column(name = "nombre", nullable = false, length = 25)
    private String name;

    @Column(name = "direccion")
    private String address;

    @Column(name = "correo")
    private String email;
}