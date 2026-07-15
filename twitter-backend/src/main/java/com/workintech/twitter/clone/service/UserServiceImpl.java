package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.UserPatchDto;
import com.workintech.twitter.clone.dto.UserRequestDto;
import com.workintech.twitter.clone.dto.UserResponseDto;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.exception.UserNotFoundException;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(user -> new UserResponseDto(user.getUserName(), user.getEmail(), user.getDisplayName(), user.getPhoneNumber())).toList();
    }

    @Override
    public UserResponseDto login(String userName, String password) {
        return null;
    }

    @Override
    public UserResponseDto get(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return new UserResponseDto(user.getUserName(), user.getEmail(), user.getDisplayName(), user.getPhoneNumber());
        }
            throw new UserNotFoundException("User Bulunamadı. id: " + id);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
         User user = new User();
         user.setUserName(userRequestDto.userName());
         user.setEmail(userRequestDto.email());
         user.setDisplayName(userRequestDto.displayName());
         user.setPhoneNumber(userRequestDto.phoneNumber());
         user.setPasswordHash(passwordEncoder.encode(userRequestDto.password()));
         user = userRepository.save(user);
         return new UserResponseDto(user.getUserName(), user.getEmail(), user.getDisplayName(), user.getPhoneNumber());
    }

    @Override
    public UserResponseDto replaceOrCreate(Long id, UserRequestDto userRequestDto) {
        Optional<User> optionalUser = userRepository.findById(id);

        User user = new User();
        user.setUserName(userRequestDto.userName());
        user.setEmail(userRequestDto.email());
        user.setDisplayName(userRequestDto.displayName());
        user.setPhoneNumber(userRequestDto.phoneNumber());
        user.setPasswordHash(passwordEncoder.encode(userRequestDto.password()));

        if(optionalUser.isPresent()) {
            user.setId(id);
            user = userRepository.save(user);
        } else {
            user = userRepository.save(user);
        }
        return new UserResponseDto(
                user.getUserName(),
                user.getEmail(),
                user.getDisplayName(),
                user.getPhoneNumber()
        );
    }

    @Override
    public UserResponseDto update(Long id, UserPatchDto userPatchDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı. id: " + id));
        if (userPatchDto.userName() != null && !userPatchDto.userName().isBlank()) {
            user.setUserName(userPatchDto.userName());
        }
        if (userPatchDto.email() != null) {
            user.setEmail(userPatchDto.email());
        }
        if (userPatchDto.displayName() != null) {
            user.setDisplayName(userPatchDto.displayName());
        }
        if (userPatchDto.bio() != null) {
            user.setBio(userPatchDto.bio());
        }
        if (userPatchDto.phoneNumber() != null) {
            user.setPhoneNumber(userPatchDto.phoneNumber());
        }
        if (userPatchDto.birthDate() != null) {
            user.setBirthDate(userPatchDto.birthDate());
        }
        if (userPatchDto.profileImage() != null) {
            user.setProfileImage(userPatchDto.profileImage());
        }
        if (userPatchDto.coverImage() != null) {
            user.setCoverImage(userPatchDto.coverImage());
        }
        if (userPatchDto.webSite() != null) {
            user.setWebSite(userPatchDto.webSite());
        }
        user = userRepository.save(user);

        return new UserResponseDto(
                user.getUserName(),
                user.getEmail(),
                user.getDisplayName(),
                user.getPhoneNumber()
        );
    }


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
