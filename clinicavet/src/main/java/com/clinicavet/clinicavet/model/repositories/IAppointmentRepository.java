package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentRepository {
    List<Appointment> findByPetId(int petId);
    List<Appointment> findByStatus(Appointment.Status status);
    List<Appointment> findByUserId(int userId);
    List<Appointment> findByDateBetween(LocalDateTime start, LocalDateTime end);
}