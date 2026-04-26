package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Pet;

import java.util.List;

public interface IPetRepository {
    List<Pet> findByOwnerId(int ownerId);
    List<Pet> findByBreedId(int breedId);
    List<Pet> findByActiveTrue();
    List<Pet> findByOwnerIdAndActiveTrue(int ownerId);
}
