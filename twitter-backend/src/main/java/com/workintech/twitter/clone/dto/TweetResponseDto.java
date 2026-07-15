package com.workintech.twitter.clone.dto;

import java.time.Instant;

public record TweetResponseDto(String content, Long inReplyToTweet, Instant createdAt){

}
