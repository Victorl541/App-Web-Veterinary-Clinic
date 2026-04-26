package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer>, IBreedRepository {
}