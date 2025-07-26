package com.security.authentication.utility;

import com.security.authentication.model.AppRoles;
import com.security.authentication.model.Role;
import com.security.authentication.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initialRoles() {

        if (roleRepository.findAll().isEmpty()) {

            Role userRole = Role.builder()
                    .roleName(AppRoles.ROLE_USER)
                    .build();
            roleRepository.save(userRole);

            Role adminRole = Role.builder()
                    .roleName(AppRoles.ROLE_ADMIN)
                    .build();
            roleRepository.save(adminRole);

        }

    }

}
