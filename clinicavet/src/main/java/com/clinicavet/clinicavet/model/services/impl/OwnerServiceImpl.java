package com.clinicavet.clinicavet.model.services.impl;

import com.clinicavet.clinicavet.model.entities.Owner;
import com.clinicavet.clinicavet.model.repositories.OwnerRepository;
import com.clinicavet.clinicavet.model.repositories.PetRepository;
import com.clinicavet.clinicavet.model.services.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements IOwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<Owner> findAll() {
        try {
            return ownerRepository.findByActiveTrue();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener dueños", e);
        }
    }

    @Override
    public Owner findById(int id) {
        try {
            return ownerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Dueño no encontrado con id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar dueño por id", e);
        }
    }

    @Override
    public Owner findByCedula(String cedula) {
        try {
            return ownerRepository.findByCedula(cedula);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al buscar dueño por cedula", e);
        }
    }

    @Override
    public void save(Owner owner) {
        try {
            ownerRepository.save(owner);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar dueño", e);
        }
    }

    @Override
    public void deactivate(int id) {
        try {
            Owner owner = findById(id);
            owner.setActive(false);
            ownerRepository.save(owner);
            // Desactivar mascotas asociadas
            owner.getPets().forEach(pet -> {
                pet.setActive(false);
                petRepository.save(pet);
            });
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al desactivar dueño", e);
        }
    }
}