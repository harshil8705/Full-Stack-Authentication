package com.security.authentication.repository;

import com.security.authentication.model.TempUser;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Long> {

    Optional<TempUser> findByEmail(@Email @NotBlank @Size(max = 50) String email);

    @Transactional
    void deleteByEmail(String email);

}
