package com.workintech.twitter.clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "comments", schema = "twitter_schema")
public class Comment {
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    @ToString.Exclude
    private Tweet tweet;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @ToString.Exclude
    private Comment parentComment;

    @JsonIgnore
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> replies = new ArrayList<>();

    public void addReply(Comment reply) {
        if (reply == null) return;
        replies.add(reply);
        reply.setParentComment(this);
        reply.setTweet(this.tweet);
    }
    public void removeReply(Comment reply) {
        if (reply == null) return;
        replies.remove(reply);
        reply.setParentComment(null);
    }
    public List<Comment> getReplies(){
        return Collections.unmodifiableList(this.replies);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comment comment = (Comment) obj;
        return comment.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
