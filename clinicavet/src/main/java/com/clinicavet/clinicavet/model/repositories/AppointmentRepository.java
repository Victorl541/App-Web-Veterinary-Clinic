package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>, IAppointmentRepository {
}