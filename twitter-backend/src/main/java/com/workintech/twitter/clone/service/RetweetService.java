package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.LikeResponseDto;
import com.workintech.twitter.clone.dto.RetweetResponseDto;

public interface RetweetService {
    RetweetResponseDto retweet(Long userId, Long tweetId);
    void unretweet(Long userId, Long tweetId);
    long count(Long tweetId);
}
