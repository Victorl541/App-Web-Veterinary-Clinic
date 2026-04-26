package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Species;

import java.util.List;

public interface ISpeciesService {
    List<Species> findAll();
    Species findById(int id);
    Species findByName(String name);
    void save(Species species);
}