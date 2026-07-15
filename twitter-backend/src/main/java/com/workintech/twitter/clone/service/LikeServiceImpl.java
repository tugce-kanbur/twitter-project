package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.LikeResponseDto;
import com.workintech.twitter.clone.entity.Like;
import com.workintech.twitter.clone.entity.Tweet;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.exception.LikeNotFoundException;
import com.workintech.twitter.clone.exception.TweetNotFoundException;
import com.workintech.twitter.clone.exception.UserNotFoundException;
import com.workintech.twitter.clone.repository.LikeRepository;
import com.workintech.twitter.clone.repository.TweetRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    @Autowired
    private final LikeRepository likeRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TweetRepository tweetRepository;


    @Override
    public LikeResponseDto like(Long userId, Long tweetId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User bulunamadı: " + userId));
    Tweet tweet = tweetRepository.findById(tweetId)
            .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı: " + tweetId));
    Like like = new Like();
    like.setUser(user);
    like.setTweet(tweet);

    likeRepository.save(like);
       return new LikeResponseDto(like.getTweet().getId(), like.getUser().getId(), like.getCreatedAt());
    }

    @Override
    public void unlike(Long userId, Long tweetId) {
        Like like = (Like) likeRepository.findByUser_IdAndTweet_Id(userId, tweetId)
                .orElseThrow(() -> new LikeNotFoundException("Like kaydı bulunamadı!"));
        likeRepository.delete(like);
    }

    @Override
    public long count(Long tweetId) {
        return likeRepository.countByTweetId(tweetId);
    }
}
