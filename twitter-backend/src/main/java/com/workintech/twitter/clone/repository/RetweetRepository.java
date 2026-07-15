package com.workintech.twitter.clone.repository;

import com.workintech.twitter.clone.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    long countByTweetId(Long tweetId);
    Optional<Object> findByUser_IdAndTweet_Id(Long userId, Long tweetId);
}
