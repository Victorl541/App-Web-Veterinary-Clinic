package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idespecie")
    private int id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;
}