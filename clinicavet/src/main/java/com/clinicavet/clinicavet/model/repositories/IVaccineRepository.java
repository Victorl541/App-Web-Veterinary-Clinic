package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Vaccine;

public interface IVaccineRepository {
    Vaccine findByName(String name);
}
