package com.workintech.twitter.clone.service;


import com.workintech.twitter.clone.dto.CommentRequestDto;
import com.workintech.twitter.clone.dto.CommentResponseDto;
import com.workintech.twitter.clone.entity.Comment;
import com.workintech.twitter.clone.entity.Tweet;
import com.workintech.twitter.clone.entity.User;
import com.workintech.twitter.clone.exception.CommentNotFoundException;
import com.workintech.twitter.clone.exception.TweetNotFoundException;
import com.workintech.twitter.clone.exception.UserNotFoundException;
import com.workintech.twitter.clone.repository.CommentRepository;
import com.workintech.twitter.clone.repository.TweetRepository;
import com.workintech.twitter.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    public CommentResponseDto create(Long userId,CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User bulunamadı: " + userId));

        Tweet tweet = tweetRepository.findById(commentRequestDto.tweet())
                .orElseThrow(() -> new TweetNotFoundException("Tweet bulunamadı: " + commentRequestDto.tweet()));

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.content());
        comment.setUser(user);
        comment.setTweet(tweet);

        if (commentRequestDto.parentComment() != null) {
            Comment parent = commentRepository.findById(commentRequestDto.parentComment())
                    .orElseThrow(() -> new CommentNotFoundException("Parent comment bulunamadı: " + commentRequestDto.parentComment()));

            if (!parent.getTweet().getId().equals(tweet.getId())) {
                throw new CommentNotFoundException("Reply ile parent aynı tweet'e ait olmalı");
            }

            parent.addReply(comment);
        }

        Comment saved = commentRepository.save(comment);
        Long parent = (saved.getParentComment() != null) ? saved.getParentComment().getId() : null;

        return new CommentResponseDto(
                saved.getContent(),
                saved.getTweet().getId(),
                saved.getParentComment() != null ? saved.getParentComment().getId() : null,
                saved.getCreatedAt()
        );
    }

    @Override
    public CommentResponseDto get(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Yorum bulunamadı!: " + id));

        Long tweet = (comment.getTweet() != null) ? comment.getTweet().getId() : null;
        Long parent = (comment.getParentComment() != null) ? comment.getParentComment().getId() : null;
        return new CommentResponseDto(comment.getContent(), tweet, parent, comment.getCreatedAt());
    }

    @Override
    public List<CommentResponseDto> listByTweet(Long tweet) {
        List<Comment> comments = commentRepository.findByTweet_IdOrderByCreatedAtDesc(tweet);

        List<CommentResponseDto> result = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentRequestDto = new CommentResponseDto(
                    comment.getContent(),
                    comment.getTweet().getId(),
                    comment.getParentComment().getId(),
                    comment.getCreatedAt());

            result.add(commentRequestDto);
        }
        return result;
    }

    @Override
    public List<CommentResponseDto> listByUser(Long userId) {
        List<Comment> comments = commentRepository.findByUser_IdOrderByCreatedAtDesc(userId);

        List<CommentResponseDto> result = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentRequestDto = new CommentResponseDto(
                    comment.getContent(),
                    comment.getTweet().getId(),
                    comment.getParentComment().getId(),
                    comment.getCreatedAt());

            result.add(commentRequestDto);
        }
        return result;
    }

    @Override
    public List<CommentResponseDto> listReplies(Long parentComment) {
        List<Comment> comments = commentRepository.findByParentComment_IdOrderByCreatedAtDesc(parentComment);

        List<CommentResponseDto> result = new ArrayList<>();
        for (Comment comment : comments) {
            Long parent = (comment.getParentComment() != null) ? comment.getParentComment().getId() : null;
            CommentResponseDto commentRequestDto = new CommentResponseDto(
                    comment.getContent(),
                    comment.getTweet().getId(),
                    comment.getParentComment().getId(),
                    comment.getCreatedAt());

            result.add(commentRequestDto);
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
