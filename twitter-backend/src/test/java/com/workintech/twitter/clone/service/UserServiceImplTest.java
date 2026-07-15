package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.UserResponseDto;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @Test
    void getAll() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUserName("ozge");
        user1.setDisplayName("Özge");
        user1.setEmail("ozge@mail.com");
        user1.setPhoneNumber("5551112356");

        User user2 = new User();
        user2.setId(2L);
        user2.setUserName("ali");
        user2.setDisplayName("Ali");
        user2.setEmail("ali@mail.com");
        user2.setPhoneNumber("444");

        when(userRepository.findAll()).thenReturn(List.of(user1,user2));
        List<UserResponseDto> dtos = userService.getAll();

        assertEquals(2, dtos.size());
        assertEquals("ozge", dtos.get(0).userName());
        assertEquals("ozge@mail.com", dtos.get(0).email());

    }

    @Test
    void create() {
    }

    @Test
    void login() {
    }

    @Test
    void get() {
    }

    @Test
    void replaceOrCreate() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        userService.delete(12L);
        verify(userRepository).deleteById(12L);

    }
}


