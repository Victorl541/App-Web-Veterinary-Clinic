package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Integer>, IVaccineRepository {
}