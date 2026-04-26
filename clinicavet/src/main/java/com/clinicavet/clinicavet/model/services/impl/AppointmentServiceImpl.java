package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Appointment;
import com.clinicavet.clinicavet.model.repositories.AppointmentRepository;
import com.clinicavet.clinicavet.model.services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> findAll() {
        try {
            return appointmentRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener citas", e);
        }
    }

    @Override
    public Appointment findById(int id) {
        try {
            return appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar cita por id", e);
        }
    }

    @Override
    public List<Appointment> findByPetId(int petId) {
        try {
            return appointmentRepository.findByPetId(petId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar citas por mascota", e);
        }
    }

    @Override
    public List<Appointment> findByStatus(Appointment.Status status) {
        try {
            return appointmentRepository.findByStatus(status);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar citas por estado", e);
        }
    }

    @Override
    public List<Appointment> findByUserId(int userId) {
        try {
            return appointmentRepository.findByUserId(userId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar citas por usuario", e);
        }
    }

    @Override
    public void save(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar cita", e);
        }
    }

    @Override
    public void cancel(int id) {
        try {
            Appointment appointment = findById(id);
            appointment.setStatus(Appointment.Status.cancelada);
            appointmentRepository.save(appointment);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al cancelar cita", e);
        }
    }
}