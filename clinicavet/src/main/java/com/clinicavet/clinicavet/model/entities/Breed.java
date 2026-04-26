package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "razas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idraza")
    private int id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "idespecie", nullable = false)
    private Species species;
}