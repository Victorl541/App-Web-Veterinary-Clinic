package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Diagnosis;

public interface IDiagnosisRepository {
    Diagnosis findByName(String name);
}