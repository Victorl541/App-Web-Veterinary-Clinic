package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Allergy;

import java.util.List;

public interface IAllergyService {
    List<Allergy> findAll();
    Allergy findById(int id);
    Allergy findByName(String name);
    void save(Allergy allergy);
}