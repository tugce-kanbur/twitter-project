package com.workintech.twitter.clone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "retweets", schema = "twitter_schema", uniqueConstraints = {
        @UniqueConstraint(name = "uq_retweets_user_tweet", columnNames = "user_id"),
        @UniqueConstraint(name = "uq_retweets_user_tweet", columnNames = "tweet_id")})
public class Retweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Retweet retweet = (Retweet) obj;
        return retweet.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
