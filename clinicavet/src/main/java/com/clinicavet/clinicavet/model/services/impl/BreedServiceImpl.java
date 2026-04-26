package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Breed;
import com.clinicavet.clinicavet.model.repositories.BreedRepository;
import com.clinicavet.clinicavet.model.services.IBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedServiceImpl implements IBreedService {

    @Autowired
    private BreedRepository breedRepository;

    @Override
    public List<Breed> findAll() {
        try {
            return breedRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener razas", e);
        }
    }

    @Override
    public Breed findById(int id) {
        try {
            return breedRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Raza no encontrada con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar raza por id", e);
        }
    }

    @Override
    public Breed findByName(String name) {
        try {
            return breedRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar raza por nombre", e);
        }
    }

    @Override
    public List<Breed> findBySpeciesId(int speciesId) {
        try {
            return breedRepository.findBySpeciesId(speciesId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar razas por especie", e);
        }
    }

    @Override
    public void save(Breed breed) {
        try {
            breedRepository.save(breed);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar raza", e);
        }
    }
}