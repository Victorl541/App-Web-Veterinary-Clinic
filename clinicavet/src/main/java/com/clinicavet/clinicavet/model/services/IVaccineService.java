package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Vaccine;

import java.util.List;

public interface IVaccineService {
    List<Vaccine> findAll();
    Vaccine findById(int id);
    Vaccine findByName(String name);
    void save(Vaccine vaccine);
}