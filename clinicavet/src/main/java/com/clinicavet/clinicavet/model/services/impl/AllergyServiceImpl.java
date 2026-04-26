package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Allergy;
import com.clinicavet.clinicavet.model.repositories.AllergyRepository;
import com.clinicavet.clinicavet.model.services.IAllergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergyServiceImpl implements IAllergyService {

    @Autowired
    private AllergyRepository allergyRepository;

    @Override
    public List<Allergy> findAll() {
        try {
            return allergyRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener alergias", e);
        }
    }

    @Override
    public Allergy findById(int id) {
        try {
            return allergyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Alergia no encontrada con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar alergia por id", e);
        }
    }

    @Override
    public Allergy findByName(String name) {
        try {
            return allergyRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar alergia por nombre", e);
        }
    }

    @Override
    public void save(Allergy allergy) {
        try {
            allergyRepository.save(allergy);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar alergia", e);
        }
    }
}