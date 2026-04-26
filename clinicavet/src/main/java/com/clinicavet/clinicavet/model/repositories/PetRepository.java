package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>, IPetRepository {
}