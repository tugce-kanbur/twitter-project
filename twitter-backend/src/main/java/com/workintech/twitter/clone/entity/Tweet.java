package com.workintech.twitter.clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "tweets", schema = "twitter_schema")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 280)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private Set<Retweet> retweets = new HashSet<>();

    public void addRetweet(Retweet retweet){
        if(retweet == null) {
            throw new IllegalArgumentException("Retweet boş olamaz.");
        }
        if(retweet.getTweet() != this) retweet.setTweet(this);
    }
    public void removeRetweet(Retweet retweet){
        retweets.remove(retweet);
    }
    public Set<Retweet> getRetweets(){
        return Collections.unmodifiableSet(this.retweets);
    }

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    public void addLike(Like like){
        if(like == null) {
            throw new IllegalArgumentException("Like boş olamaz.");
        }
        if(like.getTweet() != this) like.setTweet(this);
    }
    public void removeLike(Like like){
        likes.remove(like);
    }
    public Set<Like> getLikes(){
        return Collections.unmodifiableSet(this.likes);
    }

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        if(comment == null) {
            throw new IllegalArgumentException("Yorum boş olamaz.");
        }
        if(comment.getTweet() != this) comment.setTweet(this);
        if(!comments.contains(comment)) comments.add(comment);
    }

    public void removeTweet(Comment comment){
        comments.remove(comment);
    }
    public List<Comment> getComments(){
        return Collections.unmodifiableList(this.comments);
    }

    @ManyToOne
    @JoinColumn(name="in_reply_to_tweet_id")
    private Tweet inReplyToTweet;

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tweet tweet = (Tweet) obj;
        return tweet.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
