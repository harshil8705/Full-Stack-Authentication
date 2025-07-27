package com.security.authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempUserId;

    private String username;
    private String email;
    private String mobileNo;
    private String password;
    private String adminKey;

    private String otp;
    private LocalDateTime otpGeneratedTime;

}
