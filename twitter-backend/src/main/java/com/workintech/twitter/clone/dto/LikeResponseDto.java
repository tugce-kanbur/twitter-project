package com.workintech.twitter.clone.dto;

import java.time.Instant;

public record LikeResponseDto(Long tweet , Long user, Instant createdAt) {
}
