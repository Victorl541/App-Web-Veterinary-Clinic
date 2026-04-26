package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Breed;

import java.util.List;

public interface IBreedService {
    List<Breed> findAll();
    Breed findById(int id);
    Breed findByName(String name);
    List<Breed> findBySpeciesId(int speciesId);
    void save(Breed breed);
}