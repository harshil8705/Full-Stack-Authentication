package com.security.authentication.request;

import com.security.authentication.model.AppRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    @Size(max = 20)
    private String username;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "mobile number must be exactly 10 digits.")
    private String mobileNo;

    @NotBlank
    @Size(max = 150)
    private String password;

    private String adminKey;

    private Set<AppRoles> userRoles = new HashSet<>();

}
