package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.TweetRequestDto;
import com.workintech.twitter.clone.dto.TweetResponseDto;

import java.util.List;

public interface TweetService {

    TweetResponseDto create(Long userId, TweetRequestDto tweetRequestDto);
    TweetResponseDto get(Long id);
    List<TweetResponseDto> listByUser(Long userId);
    List<TweetResponseDto> listReplies(Long inReplyToTweetId);
    void delete(Long id);
}
