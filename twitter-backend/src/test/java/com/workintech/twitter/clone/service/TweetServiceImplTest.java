package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.TweetResponseDto;
import com.workintech.twitter.clone.entity.Tweet;
import com.workintech.twitter.clone.repository.TweetRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;


    @Test
    void create() {
    }

    @Test
    void get() {
    }

    @Test
    void listByUser() {
        Tweet tweet1 = new Tweet();
        tweet1.setId(1L);
        tweet1.setContent("A");
        tweet1.setCreatedAt(Instant.parse("2025-05-01T10:00:00Z"));

        Tweet tweet2 = new Tweet();
        tweet2.setId(7L);
        tweet2.setContent("B");
        tweet2.setCreatedAt(Instant.parse("2025-05-02T10:00:00Z"));

        when(tweetRepository.findByUser_IdOrderByCreatedAtDesc(anyLong()))
                .thenReturn(List.of(tweet2, tweet1));

        List<TweetResponseDto> res = tweetService.listByUser(7L);

        assertEquals(2, res.size());
        assertEquals("B", res.get(0).content());
        assertEquals("A", res.get(1).content());
    }

    @Test
    void listReplies() {
    }

    @Test
    void delete() {
        tweetService.delete(1L);
        verify(tweetRepository).deleteById(1L);
    }
}
