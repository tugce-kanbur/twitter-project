package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.LikeResponseDto;

public interface LikeService {
    LikeResponseDto like(Long userId, Long tweetId);
    void unlike(Long userId, Long tweetId);
    long count(Long tweetId);
}
