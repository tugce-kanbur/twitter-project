package com.workintech.twitter.clone.controller;

import com.workintech.twitter.clone.dto.TweetRequestDto;
import com.workintech.twitter.clone.dto.TweetResponseDto;
import com.workintech.twitter.clone.service.TweetService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
    @Autowired
    private final TweetService tweetService;

    @GetMapping("/{id}")
    public TweetResponseDto get(@Positive @PathVariable("id") Long id) {
        return tweetService.get(id);
    }

    @GetMapping("/users/{userId}")
    public List<TweetResponseDto> listByUser(@PathVariable @Positive Long userId) {
        return tweetService.listByUser(userId);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> listReplies(@PathVariable("id") @Positive Long inReplyToTweet) {
        return tweetService.listReplies(inReplyToTweet);
    }

    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public TweetResponseDto create(
            @PathVariable @Positive Long userId,
            @RequestBody @Validated TweetRequestDto tweetRequestDto) {
        return tweetService.create(userId, tweetRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)   //204
    public void delete(@Positive @PathVariable("id") Long id){
        tweetService.delete(id);
    }
}
