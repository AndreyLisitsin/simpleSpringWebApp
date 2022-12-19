package com.lisitsin.simpleRestWithSpring.repository;

import com.lisitsin.simpleRestWithSpring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
