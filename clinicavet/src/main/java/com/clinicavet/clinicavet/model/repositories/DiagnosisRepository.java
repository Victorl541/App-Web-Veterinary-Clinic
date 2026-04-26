package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer>, IDiagnosisRepository {
}