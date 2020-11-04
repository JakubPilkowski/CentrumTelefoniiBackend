package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.dao.JwtBlackListRepo;
import com.example.centrumtelefonii.dao.RoleRepository;
import com.example.centrumtelefonii.dao.UserRepository;
import com.example.centrumtelefonii.exception.AppException;
import com.example.centrumtelefonii.models.JwtBlacklist;
import com.example.centrumtelefonii.models.Role;
import com.example.centrumtelefonii.models.RoleName;
import com.example.centrumtelefonii.models.User;
import com.example.centrumtelefonii.payload.ApiResponse;
import com.example.centrumtelefonii.payload.JwtAuthenticationResponse;
import com.example.centrumtelefonii.payload.LoginRequest;
import com.example.centrumtelefonii.payload.SignUpRequest;
import com.example.centrumtelefonii.security.JwtTokenProvider;
import com.example.centrumtelefonii.security.UserPrincipal;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthenticationManager authenticationManager;

    JwtBlackListRepo jwtBlackListRepo;

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider,
                          JwtBlackListRepo jwtBlackListRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.jwtBlackListRepo = jwtBlackListRepo;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);

        if (userRole.isPresent()) {
            user.setRoles(Collections.singleton(userRole.get()));
        } else {
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Usunięto pomyślnie użytkownika"));
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String jwtToken) {
        JwtBlacklist jwtBlacklist = new JwtBlacklist();
        jwtBlacklist.setToken(jwtToken.substring(7));
        jwtBlackListRepo.save(jwtBlacklist);
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie wylogowano"));
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<?> isLoggedIn(@RequestHeader("Authorization") String jwttoken) {
        if (tokenProvider.validateToken(jwttoken.substring(7)))
            return ResponseEntity.ok(new ApiResponse(true, "Jesteś zalogowany"));
        else
            return ResponseEntity.ok(new ApiResponse(false, "Nie jesteś zalogowany"));
    }
}
