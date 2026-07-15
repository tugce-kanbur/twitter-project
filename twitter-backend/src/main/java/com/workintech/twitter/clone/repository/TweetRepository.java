package com.workintech.twitter.clone.repository;

import com.workintech.twitter.clone.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByInReplyToTweet_IdOrderByCreatedAtDesc(Long inReplyToTweetId);

    List<Tweet> findByUser_IdOrderByCreatedAtDesc(Long userId);
}
