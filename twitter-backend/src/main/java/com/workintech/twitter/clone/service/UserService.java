package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.UserPatchDto;
import com.workintech.twitter.clone.dto.UserRequestDto;
import com.workintech.twitter.clone.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAll();
    UserResponseDto get(Long id);
    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto replaceOrCreate(Long id, UserRequestDto userRequestDto);
    UserResponseDto update(Long id, UserPatchDto userPatchDto);
    void delete(Long id);
    UserResponseDto login (String userName, String password);
}
