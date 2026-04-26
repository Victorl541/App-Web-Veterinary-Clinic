package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.MedicalReport;

import java.util.List;

public interface IMedicalReportService {
    List<MedicalReport> findAll();
    MedicalReport findById(int id);
    List<MedicalReport> findByPetId(int petId);
    List<MedicalReport> findByUserId(int userId);
    List<MedicalReport> findByAppointmentId(int appointmentId);
    void save(MedicalReport medicalReport);
}