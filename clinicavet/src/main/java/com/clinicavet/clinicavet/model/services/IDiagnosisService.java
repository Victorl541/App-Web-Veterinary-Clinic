package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Diagnosis;

import java.util.List;

public interface IDiagnosisService {
    List<Diagnosis> findAll();
    Diagnosis findById(int id);
    Diagnosis findByName(String name);
    void save(Diagnosis diagnosis);
}