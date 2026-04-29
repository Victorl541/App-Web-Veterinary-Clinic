package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "informe_medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinforme_medico")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idmascota", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "idcita", nullable = true)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private User user;

    @Column(name = "peso", nullable = false, precision = 4, scale = 2)
    private BigDecimal weight;

    @Column(name = "anamnesis", columnDefinition = "TEXT")
    private String anamnesis;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "procedimiento", nullable = false, columnDefinition = "TEXT")
    private String procedure;

    @ManyToMany
    @JoinTable(
            name = "informe_diagnostico",
            joinColumns = @JoinColumn(name = "idinforme"),
            inverseJoinColumns = @JoinColumn(name = "iddiagnostico")
    )
    private List<Diagnosis> diagnoses;
}