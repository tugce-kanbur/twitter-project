package com.workintech.twitter.clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record RetweetRequestDto(@NotNull
                                @JsonProperty("tweet_id")
                                Long tweet,

                                @JsonProperty("user_id")
                                @NotNull
                                Long user) {
}
