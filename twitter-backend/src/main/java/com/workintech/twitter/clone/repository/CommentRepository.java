package com.workintech.twitter.clone.repository;

import com.workintech.twitter.clone.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTweet_IdOrderByCreatedAtDesc(Long tweet);

    List<Comment> findByUser_IdOrderByCreatedAtDesc(Long userId);

    List<Comment> findByParentComment_IdOrderByCreatedAtDesc(Long parentComment);
}
