package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Pet;
import com.clinicavet.clinicavet.model.repositories.PetRepository;
import com.clinicavet.clinicavet.model.services.IPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements IPetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Pet> findAll() {
        try {
            return petRepository.findByActiveTrue();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener mascotas", e);
        }
    }

    @Override
    public Pet findById(int id) {
        try {
            return petRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar mascota por id", e);
        }
    }

    @Override
    public List<Pet> findByOwnerId(int ownerId) {
        try {
            return petRepository.findByOwnerIdAndActiveTrue(ownerId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar mascotas por dueño", e);
        }
    }

    @Override
    public void save(Pet pet) {
        try {
            petRepository.save(pet);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar mascota", e);
        }
    }

    @Override
    public void deactivate(int id) {
        try {
            Pet pet = findById(id);
            pet.setActive(false);
            petRepository.save(pet);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al desactivar mascota", e);
        }
    }
}
