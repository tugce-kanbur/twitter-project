package com.workintech.twitter.clone.repository;



import com.workintech.twitter.clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email OR u.userName = :userName OR u.phoneNumber = :phoneNumber")
    Optional<User> findByEmailOrUserNameOrPhoneNumber(String email, String userName, String phoneNumber);
}
