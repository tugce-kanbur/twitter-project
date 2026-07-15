package com.workintech.twitter.clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public record UserRequestDto(
                             @NotEmpty
                             @NotBlank
                             @NotNull
                             @Size(max = 15)
                             @JsonProperty("user_name")
                             String userName,
                             @Size(max = 100)
                             @Email(message = "Geçerli bir email adresi giriniz")
                             @JsonProperty("email")
                             String email,
                             @NotEmpty
                             @NotBlank
                             @NotNull
                             @Size(max = 50)
                             @JsonProperty("display_name")
                             String displayName,
                             @JsonProperty("phone_number")
                             @Size(max = 20)
                             String phoneNumber,
                             @NotEmpty
                             @NotBlank
                             @NotNull
                             @Size(max = 255)
                             @JsonProperty("password")
                             String password) {

}
