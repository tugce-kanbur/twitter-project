package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.RetweetResponseDto;
import com.workintech.twitter.clone.entity.Retweet;
import com.workintech.twitter.clone.entity.Tweet;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.exception.RetweetNotFoundException;
import com.workintech.twitter.clone.exception.TweetNotFoundException;
import com.workintech.twitter.clone.exception.UserNotFoundException;
import com.workintech.twitter.clone.repository.RetweetRepository;
import com.workintech.twitter.clone.repository.TweetRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService{
    @Autowired
    private final RetweetRepository retweetRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TweetRepository tweetRepository;
    @Override
    public RetweetResponseDto retweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı: " + userId));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı: " + tweetId));
        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        retweetRepository.save(retweet);
        return new RetweetResponseDto(retweet.getTweet().getId(), retweet.getUser().getId(), retweet.getCreatedAt());
    }

    @Override
    public void unretweet(Long userId, Long tweetId) {
        Retweet retweet = (Retweet) retweetRepository.findByUser_IdAndTweet_Id(userId, tweetId)
                .orElseThrow(() -> new RetweetNotFoundException("Like kaydı bulunamadı!"));
        retweetRepository.delete(retweet);
    }

    @Override
    public long count(Long tweetId) {
        return retweetRepository.countByTweetId(tweetId);
    }
}
