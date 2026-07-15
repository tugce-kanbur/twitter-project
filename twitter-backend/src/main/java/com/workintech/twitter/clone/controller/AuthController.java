package com.workintech.twitter.clone.controller;

import com.workintech.twitter.clone.dto.RegisterRequestDto;
import com.workintech.twitter.clone.dto.RegisterResponseDto;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.entity.Role;
import com.workintech.twitter.clone.repository.RoleRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
        userRepository.findByEmailOrUserNameOrPhoneNumber(
                registerRequestDto.email(),
                registerRequestDto.userName(),
                registerRequestDto.phoneNumber()
        ).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email, username veya phone zaten kullanılıyor");
        });

        Role roleUser = roleRepository.findByAuthority("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setAuthority("ROLE_USER");
                    return roleRepository.save(role);
                });

        User user = new User();
        user.setUserName(registerRequestDto.userName());
        user.setEmail(registerRequestDto.email());
        user.setPasswordHash(passwordEncoder.encode(registerRequestDto.password()));
        user.getRoles().add(roleUser);
        user.setDisplayName(registerRequestDto.displayName());
        user.setPhoneNumber(registerRequestDto.phoneNumber());



        user = userRepository.save(user);
        return new RegisterResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDisplayName());
    }

}
