package com.workintech.twitter.clone.controller;

import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.repository.RoleRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void register() throws Exception {
        String body = """
                {
                "userName":"tuce",
                "displayName":"Tuce",
                "email":"tuce@mail.com",
                "phoneNumber":"5551112233",
                "password":"secret" 
                } """;

        when(userRepository.findByEmailOrUserNameOrPhoneNumber("tuce@mail.com","tuce","5551112233"))
                .thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());

        verify(userRepository).findByEmailOrUserNameOrPhoneNumber("tuce@mail.com","tuce","5551112233");
        verifyNoInteractions(roleRepository, passwordEncoder);
        verify(userRepository, never()).save(any(User.class));
    }
}
