package com.workintech.twitter.clone.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommentNotFoundException extends TwitterException{
    public CommentNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
