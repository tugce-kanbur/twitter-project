package com.workintech.twitter.clone.controller;


import com.workintech.twitter.clone.dto.UserPatchDto;
import com.workintech.twitter.clone.dto.UserRequestDto;
import com.workintech.twitter.clone.dto.UserResponseDto;
import com.workintech.twitter.clone.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@Positive @PathVariable("id") Long id){
       return userService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //HTTP 201
    public UserResponseDto create(@Validated @RequestBody UserRequestDto userRequestDto){
        return userService.create(userRequestDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto replaceOrCreate(@Positive @PathVariable("id") Long id, @Validated @RequestBody UserRequestDto userRequestDto){
        return userService.replaceOrCreate(id, userRequestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto update(@Positive @PathVariable("id") Long id,
                                        @Validated @RequestBody UserPatchDto userPatchDto){
        return userService.update(id, userPatchDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT) //HTTP 204
    public void delete(@Positive @PathVariable("id") Long id){
        userService.delete(id);
    }
}
