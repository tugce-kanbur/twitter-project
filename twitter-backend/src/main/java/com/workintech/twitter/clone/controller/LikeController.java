package com.workintech.twitter.clone.controller;

import com.workintech.twitter.clone.dto.LikeResponseDto;
import com.workintech.twitter.clone.service.LikeService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/likes")

public class LikeController {
    @Autowired
    private final LikeService likeService;

    @PostMapping("/tweets/{tweetId}/users/{userId}")
    public LikeResponseDto like(@PathVariable @Positive Long tweetId,
                                @PathVariable @Positive Long userId){
        return likeService.like(tweetId, userId);
    }

    @DeleteMapping("/tweets/{tweetId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(
                               @PathVariable @Positive Long tweetId,
                               @PathVariable @Positive Long userId){
         likeService.unlike(tweetId, userId);
    }

    @GetMapping("/count/{tweetId}")
    public long count(@PathVariable Long tweetId) {
        return likeService.count(tweetId);
    }

}
