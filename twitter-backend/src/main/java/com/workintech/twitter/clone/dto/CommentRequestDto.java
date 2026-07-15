package com.workintech.twitter.clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CommentRequestDto(@NotNull
                                @NotBlank
                                @NotEmpty
                                @Size(max=280)
                                @JsonProperty("content")
                                String content,

                                @NotNull
                                @JsonProperty("tweet_id")
                                Long tweet,

                                @JsonProperty("user_id")
                                @NotNull
                                Long user,

                                @JsonProperty("parent_comment_id")
                                Long parentComment
                                ) {
}
