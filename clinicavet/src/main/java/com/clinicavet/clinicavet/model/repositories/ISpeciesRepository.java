package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Species;

public interface ISpeciesRepository {
    Species findByName(String name);
}