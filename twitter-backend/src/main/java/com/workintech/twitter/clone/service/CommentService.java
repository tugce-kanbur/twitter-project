package com.workintech.twitter.clone.service;

import com.workintech.twitter.clone.dto.CommentRequestDto;
import com.workintech.twitter.clone.dto.CommentResponseDto;
import com.workintech.twitter.clone.entity.Comment;

import java.util.List;

public interface CommentService {
    CommentResponseDto create(Long userId,CommentRequestDto dto);
    CommentResponseDto get(Long id);
    List<CommentResponseDto> listByTweet(Long tweet);
    List<CommentResponseDto> listByUser(Long userId);
    List<CommentResponseDto> listReplies(Long parentComment);
    void delete(Long commentId);
}
