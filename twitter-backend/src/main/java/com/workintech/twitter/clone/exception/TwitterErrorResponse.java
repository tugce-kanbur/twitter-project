package com.workintech.twitter.clone.exception;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TwitterErrorResponse {

    private String message;
    private int status;
    private long timestamp;
    private LocalDateTime localDateTime;
}
