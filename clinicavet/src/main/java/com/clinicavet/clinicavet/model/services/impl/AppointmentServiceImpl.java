package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Appointment;
import com.clinicavet.clinicavet.model.repositories.AppointmentRepository;
import com.clinicavet.clinicavet.model.services.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Appointment> findByDate(LocalDate date) {
        try {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(23, 59, 59);
            return appointmentRepository.findByDateBetween(start, end);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar citas por fecha", e);
        }
    }

    @Override
    public Map<String, Long> countByMonth() {
        try {
            List<Appointment> all = appointmentRepository.findAll();
            Map<String, Long> result = new LinkedHashMap<>();
            String[] months = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
            for (String month : months) result.put(month, 0L);
            all.forEach(a -> {
                String month = months[a.getDate().getMonthValue() - 1];
                result.put(month, result.get(month) + 1);
            });
            return result;
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al contar citas por mes", e);
        }
    }

}