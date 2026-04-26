package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Owner;

import java.util.List;

public interface IOwnerRepository {
    Owner findByCedula(String cedula);
    Owner findByEmail(String email);
    List<Owner> findByActiveTrue();
}