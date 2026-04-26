package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Integer>, IAllergyRepository {
}