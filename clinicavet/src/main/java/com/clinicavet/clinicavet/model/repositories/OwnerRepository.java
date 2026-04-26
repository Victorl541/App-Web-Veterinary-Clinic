package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer>, IOwnerRepository {
}