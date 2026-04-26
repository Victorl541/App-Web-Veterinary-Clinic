package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Vaccine;
import com.clinicavet.clinicavet.model.repositories.VaccineRepository;
import com.clinicavet.clinicavet.model.services.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineServiceImpl implements IVaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Override
    public List<Vaccine> findAll() {
        try {
            return vaccineRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener vacunas", e);
        }
    }

    @Override
    public Vaccine findById(int id) {
        try {
            return vaccineRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar vacuna por id", e);
        }
    }

    @Override
    public Vaccine findByName(String name) {
        try {
            return vaccineRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar vacuna por nombre", e);
        }
    }

    @Override
    public void save(Vaccine vaccine) {
        try {
            vaccineRepository.save(vaccine);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar vacuna", e);
        }
    }
}
