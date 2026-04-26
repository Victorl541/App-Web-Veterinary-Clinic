package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.MedicalReport;
import com.clinicavet.clinicavet.model.repositories.MedicalReportRepository;
import com.clinicavet.clinicavet.model.services.IMedicalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalReportServiceImpl implements IMedicalReportService {

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    @Override
    public List<MedicalReport> findAll() {
        try {
            return medicalReportRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener informes médicos", e);
        }
    }

    @Override
    public MedicalReport findById(int id) {
        try {
            return medicalReportRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Informe médico no encontrado con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar informe médico por id", e);
        }
    }

    @Override
    public List<MedicalReport> findByPetId(int petId) {
        try {
            return medicalReportRepository.findByPetId(petId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar informes por mascota", e);
        }
    }

    @Override
    public List<MedicalReport> findByUserId(int userId) {
        try {
            return medicalReportRepository.findByUserId(userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar informes por usuario", e);
        }
    }

    @Override
    public List<MedicalReport> findByAppointmentId(int appointmentId) {
        try {
            return medicalReportRepository.findByAppointmentId(appointmentId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar informes por cita", e);
        }
    }

    @Override
    public void save(MedicalReport medicalReport) {
        try {
            medicalReportRepository.save(medicalReport);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar informe médico", e);
        }
    }

}