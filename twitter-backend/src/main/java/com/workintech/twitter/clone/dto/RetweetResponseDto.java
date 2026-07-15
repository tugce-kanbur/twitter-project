package com.workintech.twitter.clone.dto;

import java.time.Instant;

public record RetweetResponseDto(Long tweet , Long user, Instant createdAt) {
}
