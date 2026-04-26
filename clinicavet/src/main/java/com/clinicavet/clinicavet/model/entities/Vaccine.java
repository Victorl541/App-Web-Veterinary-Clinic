package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vacunas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvacunas")
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;
}
