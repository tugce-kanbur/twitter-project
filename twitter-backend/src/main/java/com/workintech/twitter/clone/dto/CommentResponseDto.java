package com.workintech.twitter.clone.dto;


import java.time.Instant;

public record CommentResponseDto(String content, Long tweet,Long parentComment, Instant createdAt) {
}
