package com.workintech.twitter.clone.dto;

import java.time.LocalDate;

public record UserPatchDto(String userName, String email, String displayName, String bio, String phoneNumber, LocalDate birthDate, String profileImage, String coverImage, String webSite){
}
