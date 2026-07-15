package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.TweetRequestDto;
import com.workintech.twitter.clone.dto.TweetResponseDto;
import com.workintech.twitter.clone.entity.Tweet;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.exception.TweetNotFoundException;
import com.workintech.twitter.clone.exception.UserNotFoundException;
import com.workintech.twitter.clone.repository.TweetRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public TweetResponseDto create(Long userId, TweetRequestDto tweetRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı: " + userId));

        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(tweetRequestDto.content());

        if (tweetRequestDto.inReplyToTweet() != null) {
            Long parentId = tweetRequestDto.inReplyToTweet();
            Tweet parent = tweetRepository.findById(parentId)
                    .orElseThrow(() -> new TweetNotFoundException("Parent tweet bulunamadı: " + parentId));
            tweet.setInReplyToTweet(parent);
        }

        Tweet saved = tweetRepository.save(tweet);

        Long parentId = (saved.getInReplyToTweet() != null) ? saved.getInReplyToTweet().getId() : null;
        return new TweetResponseDto(
                saved.getContent(),
                parentId,                    //  null-güvenli
                saved.getCreatedAt()
        );
    }

    @Override
    public TweetResponseDto get(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı: " + id));

        Long parentId = (tweet.getInReplyToTweet() != null) ? tweet.getInReplyToTweet().getId() : null;
        return new TweetResponseDto(
                tweet.getContent(),
                parentId,                    //  null-güvenli
                tweet.getCreatedAt()
        );
    }

    @Override
    public List<TweetResponseDto> listByUser(Long userId) {
        List<Tweet> tweets = tweetRepository.findByUser_IdOrderByCreatedAtDesc(userId);

        List<TweetResponseDto> result = new ArrayList<>();
        for (Tweet t : tweets) {
            Long parentId = (t.getInReplyToTweet() != null) ? t.getInReplyToTweet().getId() : null;
            result.add(new TweetResponseDto(
                    t.getContent(),
                    parentId,                 //  null-güvenli
                    t.getCreatedAt()
            ));
        }
        return result;
    }

    @Override
    public List<TweetResponseDto> listReplies(Long inReplyToTweetId) {
        List<Tweet> replies = tweetRepository.findByInReplyToTweet_IdOrderByCreatedAtDesc(inReplyToTweetId);

        List<TweetResponseDto> result = new ArrayList<>();
        for (Tweet r : replies) {
            Long parentId = (r.getInReplyToTweet() != null) ? r.getInReplyToTweet().getId() : null;
            result.add(new TweetResponseDto(
                    r.getContent(),
                    parentId,                 // <-- null-güvenli
                    r.getCreatedAt()
            ));
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        tweetRepository.deleteById(id);
    }
}

