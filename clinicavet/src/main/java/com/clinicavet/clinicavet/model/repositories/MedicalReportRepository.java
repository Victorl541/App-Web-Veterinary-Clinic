package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Integer>, IMedicalReportRepository {
}
