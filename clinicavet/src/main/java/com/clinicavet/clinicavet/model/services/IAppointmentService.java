package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Appointment;

import java.util.List;

public interface IAppointmentService {
    List<Appointment> findAll();
    Appointment findById(int id);
    List<Appointment> findByPetId(int petId);
    List<Appointment> findByStatus(Appointment.Status status);
    List<Appointment> findByUserId(int userId);
    void save(Appointment appointment);
    void cancel(int id);
}