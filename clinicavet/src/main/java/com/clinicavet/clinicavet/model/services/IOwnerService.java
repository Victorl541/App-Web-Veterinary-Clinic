package com.clinicavet.clinicavet.model.services;

import com.clinicavet.clinicavet.model.entities.Owner;

import java.util.List;

public interface IOwnerService {
    List<Owner> findAll();
    Owner findById(int id);
    Owner findByCedula(String cedula);
    void save(Owner owner);
    void deactivate(int id);
}
