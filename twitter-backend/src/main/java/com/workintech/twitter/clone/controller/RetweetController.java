package com.workintech.twitter.clone.controller;

import com.workintech.twitter.clone.dto.RetweetResponseDto;
import com.workintech.twitter.clone.service.RetweetService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/retweet")
public class RetweetController {
    @Autowired
    private final RetweetService retweetService;

    @PostMapping("/tweets/{tweetId}/users/{userId}")
    public RetweetResponseDto retweet(@PathVariable @Positive Long tweetId,
                                   @PathVariable @Positive Long userId){
        return retweetService.retweet(tweetId, userId);
    }

    @DeleteMapping("/tweets/{tweetId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unretweet(@PathVariable @Positive Long tweetId,
                       @PathVariable @Positive Long userId){
        retweetService.unretweet(tweetId, userId);
    }

    @GetMapping("/count/{tweetId}")
    public long count(@PathVariable Long tweetId) {
        return retweetService.count(tweetId);
    }
}
