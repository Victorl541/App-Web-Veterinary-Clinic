package com.clinicavet.clinicavet.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Period;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmascotas")
    private int id;

    @Column(name = "nombre", nullable = false, length = 15)
    private String name;

    @Column(name = "genero", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "fecha_naci")
    private LocalDate birthDate;

    @Column(name = "activo", nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "idraza", nullable = false)
    private Breed breed;

    @ManyToOne
    @JoinColumn(name = "idduenio", nullable = false)
    private Owner owner;

    public enum Gender {
        macho, hembra
    }

    public int getAge() {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @ManyToMany
    @JoinTable(
            name = "mascota_alergias",
            joinColumns = @JoinColumn(name = "idmascota"),
            inverseJoinColumns = @JoinColumn(name = "idalergia")
    )
    private List<Allergy> allergies;

    @ManyToMany
    @JoinTable(
            name = "mascota_vacunas",
            joinColumns = @JoinColumn(name = "idmascota"),
            inverseJoinColumns = @JoinColumn(name = "idvacuna")
    )
    private List<Vaccine> vaccines;

}
