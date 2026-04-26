package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Breed;

import java.util.List;

public interface IBreedRepository {
    List<Breed> findBySpeciesId(int speciesId);
    Breed findByName(String name);
}
