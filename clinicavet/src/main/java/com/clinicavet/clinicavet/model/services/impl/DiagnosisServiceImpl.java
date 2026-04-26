package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Diagnosis;
import com.clinicavet.clinicavet.model.repositories.DiagnosisRepository;
import com.clinicavet.clinicavet.model.services.IDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisServiceImpl implements IDiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Override
    public List<Diagnosis> findAll() {
        try {
            return diagnosisRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener diagnósticos", e);
        }
    }

    @Override
    public Diagnosis findById(int id) {
        try {
            return diagnosisRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar diagnóstico por id", e);
        }
    }

    @Override
    public Diagnosis findByName(String name) {
        try {
            return diagnosisRepository.findByName(name);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar diagnóstico por nombre", e);
        }
    }

    @Override
    public void save(Diagnosis diagnosis) {
        try {
            diagnosisRepository.save(diagnosis);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar diagnóstico", e);
        }
    }
}
