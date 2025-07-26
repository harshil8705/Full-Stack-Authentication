package com.security.authentication.repository;

import com.security.authentication.model.AppRoles;
import com.security.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRolesName(AppRoles roles);

}
