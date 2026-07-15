package com.workintech.twitter.clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TweetRequestDto(
        @NotEmpty
        @NotBlank
        @NotNull
        @Size(max = 280)
        @JsonProperty("content")
        String content,
        @JsonProperty("in_reply_to_tweet")
        Long inReplyToTweet) {
}
