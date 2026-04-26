package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.MedicalReport;

import java.util.List;

public interface IMedicalReportRepository {
    List<MedicalReport> findByPetId(int petId);
    List<MedicalReport> findByUserId(int userId);
    List<MedicalReport> findByAppointmentId(int appointmentId);
}
