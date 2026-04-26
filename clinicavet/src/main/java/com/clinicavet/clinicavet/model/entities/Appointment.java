package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcita")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idmascota", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private User user;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        pendiente, en_curso, completada, cancelada
    }
}