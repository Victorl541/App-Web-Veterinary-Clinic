package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer>, ISpeciesRepository {
}