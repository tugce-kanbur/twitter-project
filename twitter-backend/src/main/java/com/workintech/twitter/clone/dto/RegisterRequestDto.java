package com.workintech.twitter.clone.dto;

import jakarta.validation.constraints.*;

public record RegisterRequestDto(
                                 @NotNull
                                 @NotEmpty
                                 @NotBlank
                                 @Email
                                 @Size(max = 100)
                                 String email,
                                 @NotNull
                                 @NotEmpty
                                 @NotBlank
                                 @Size(max = 300)
                                 String password,
                                 @NotBlank
                                 @Size(max = 15)
                                 String userName,
                                 @NotNull
                                 @NotEmpty
                                 @NotBlank
                                 @Size(max = 20)
                                 String phoneNumber,
                                 @NotBlank
                                 @Size(max=50)
                                 String displayName
)
     {
}
