package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alergias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalergias")
    private int id;

    @Column(name = "nombre", nullable = false, length = 40)
    private String name;
}
