package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Allergy;

public interface IAllergyRepository {
    Allergy findByName(String name);
}