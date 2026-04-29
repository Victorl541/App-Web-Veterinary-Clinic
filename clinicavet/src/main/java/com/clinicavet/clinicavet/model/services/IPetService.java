package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Pet;

import java.util.List;
import java.util.Map;

public interface IPetService {
    List<Pet> findAll();
    Pet findById(int id);
    List<Pet> findByOwnerId(int ownerId);
    void save(Pet pet);
    void deactivate(int id);
    Map<String, Long> countBySpecies();
}
