package com.security.authentication.controller;

import com.security.authentication.jwt.JwtUtils;
import com.security.authentication.model.AppRoles;
import com.security.authentication.model.Role;
import com.security.authentication.model.User;
import com.security.authentication.repository.RoleRepository;
import com.security.authentication.repository.UserRepository;
import com.security.authentication.request.LoginRequest;
import com.security.authentication.request.SignupRequest;
import com.security.authentication.response.MessageResponse;
import com.security.authentication.response.UserInfoResponse;
import com.security.authentication.service.UserDetailsImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${spring.app.adminKey}")
    private String adminKey;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid LoginRequest loginRequest) {

        Authentication authentication;

        try{

            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

        } catch (AuthenticationException e) {

            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad Credentials");
            map.put("status", false);

            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie cookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserInfoResponse response = UserInfoResponse.builder()
                .userId(userDetails.getUserId())
                .username(userDetails.getUsername())
                .jwtToken(cookie.toString())
                .roles(roles)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);

    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {

            return new ResponseEntity<>(new MessageResponse("Username Already Taken"), HttpStatus.BAD_REQUEST);

        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {

            return new ResponseEntity<>(new MessageResponse("Email Already Exists"), HttpStatus.BAD_REQUEST);

        }

        if (userRepository.existsByMobileNo(signupRequest.getMobileNo())) {

            return new ResponseEntity<>(new MessageResponse("Mobile Number already Exists"), HttpStatus.BAD_REQUEST);

        }

        User newUser = User.builder()
                .email(signupRequest.getEmail())
                .mobileNo(signupRequest.getMobileNo())
                .password(encoder.encode(signupRequest.getPassword()))
                .username(signupRequest.getUsername())
                .build();
        Set<Role> roles = new HashSet<>();

        if (adminKey.equals(signupRequest.getAdminKey())) {

            List<Role> everyRoles = roleRepository.findAll();

            if (everyRoles.isEmpty()) {

                throw new RuntimeException("Error : No Roles Found");

            }

            roles.addAll(everyRoles);

        } else {

            Role userRole = roleRepository.findByRolesName(AppRoles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error : No Roles Found"));

            roles.add(userRole);

        }

        newUser.setUserRoles(roles);
        userRepository.save(newUser);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);

    }

    @GetMapping("/username")
    public ResponseEntity<?> currentUserName(Authentication authentication) {

        if (authentication != null) {

            return new ResponseEntity<>(authentication.getName(), HttpStatus.OK);

        }

        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);

    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserInfoResponse response = UserInfoResponse.builder()
                .userId(userDetails.getUserId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOutUser() {

        ResponseCookie cookie = jwtUtils.generateCleanJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));

    }

}
