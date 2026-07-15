package com.workintech.twitter.clone.controller;

import com.workintech.twitter.clone.dto.CommentRequestDto;
import com.workintech.twitter.clone.dto.CommentResponseDto;
import com.workintech.twitter.clone.service.CommentService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Validated
public class CommentController {
  @Autowired
  private final CommentService commentService;

  @PostMapping("/users/{userId}/comments")
  @ResponseStatus(HttpStatus.CREATED) // 201
  public CommentResponseDto create(
          @PathVariable @Positive Long userId,
          @RequestBody @Validated CommentRequestDto commentRequestDto) {

    return commentService.create(userId, commentRequestDto);
  }

  @GetMapping("/{id}")
  public CommentResponseDto get(@Positive @PathVariable("id") Long id) {
    return commentService.get(id);
  }

  @GetMapping("/tweets/{tweet}/comments")
  public List<CommentResponseDto> listByTweet(@Positive @PathVariable Long tweet) {
    return commentService.listByTweet(tweet);
  }


  @GetMapping("/users/{userId}/comments")
  public List<CommentResponseDto> listByUser(@PathVariable("userId") @Positive Long userId) {
    return commentService.listByUser(userId);
  }

  @GetMapping("/{parentComment}/replies")
  public List<CommentResponseDto> listReplies(@Positive @PathVariable("parentComment") Long parentComment  ) {
    return commentService.listReplies(parentComment);
  }


  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)   //204
  public void delete(@Positive @PathVariable("id") Long id){
    commentService.delete(id);
  }
}
