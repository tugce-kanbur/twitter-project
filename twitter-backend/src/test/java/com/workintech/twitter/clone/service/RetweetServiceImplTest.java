package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.repository.RetweetRepository;
import com.workintech.twitter.clone.repository.TweetRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetweetServiceImplTest {

    @InjectMocks
    private RetweetServiceImpl retweetService;

    @Mock
    private RetweetRepository retweetRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TweetRepository tweetRepository;

    @Test
    void retweet() {
    }

    @Test
    void unretweet() {
    }

    @Test
    void count() {
        when(retweetRepository.countByTweetId(77L)).thenReturn(4L);
        assertEquals(4L, retweetService.count(77L));
        verify(retweetRepository).countByTweetId(77L);
    }
}
