package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmailOrUserNameOrPhoneNumber(identifier, identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + identifier));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPasswordHash())
                .authorities(user.getRoles())
                .build();
    }
}