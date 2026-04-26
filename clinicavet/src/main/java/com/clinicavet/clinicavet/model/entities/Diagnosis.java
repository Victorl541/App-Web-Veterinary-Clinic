package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diagnosticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiagnosticos")
    private int id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String name;
}