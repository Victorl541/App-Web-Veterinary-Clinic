package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, IRoleRepository {
}