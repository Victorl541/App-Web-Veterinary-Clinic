package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Species;
import com.clinicavet.clinicavet.model.repositories.SpeciesRepository;
import com.clinicavet.clinicavet.model.services.ISpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesServiceImpl implements ISpeciesService {

    @Autowired
    private SpeciesRepository speciesRepository;

    @Override
    public List<Species> findAll() {
        try {
            return speciesRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener especies", e);
        }
    }

    @Override
    public Species findById(int id) {
        try {
            return speciesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Especie no encontrada con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar especie por id", e);
        }
    }

    @Override
    public Species findByName(String name) {
        try {
            return speciesRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar especie por nombre", e);
        }
    }

    @Override
    public void save(Species species) {
        try {
            speciesRepository.save(species);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar especie", e);
        }
    }

}
