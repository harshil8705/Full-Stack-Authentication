package com.security.authentication.repository;

import com.security.authentication.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(@NotBlank @Size(max = 20) String username);

    boolean existsByEmail(@Email @NotBlank @Size(max = 50) String email);

    boolean existsByMobileNo(@NotBlank @Pattern(regexp = "\\d{10}", message = "mobile number must be exactly 10 digits.") String mobileNo);

}
