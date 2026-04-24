package com.clinicavet.clinicavet.model.repositories;

import com.clinicavet.clinicavet.model.entities.Role;

public interface IRoleRepository {
    Role findByName(String name);
}
