package com.workintech.twitter.clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Table(name = "users", schema = "twitter_schema", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_number")
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 15)
    @Pattern(regexp = "^[A-Za-z0-9_]{1,15}$", message = "Kullanıcı adı sadece harf, rakam ve alt çizgi içerebilir, 1-15 karakter olmalıdır")
    private String userName;

    @Column(name = "email")
    @Size(max = 100)
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email;

    @Column(name = "display_name")
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 50)
    private String displayName;

    @Column(name = "bio")
    @Size(max = 160)
    private String bio;

    @Column(name = "phone_number")
    @Size(max = 20)
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Telefon numarası en az 10 rakamdan oluşmalı, isteğe bağlı olarak + ile başlayabilir"
    )
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_image")
    @Size(max = 255)
    private String profileImage;

    @Column(name = "cover_image")
    @Size(max = 255)
    private String coverImage;


    @Column(name = "web_site")
    @Size(max = 255)
    @Pattern(
            regexp = "^(https?://).*$",
            message = "Web sitesi http:// veya https:// ile başlamalıdır"
    )
    private String webSite;

    @Column(name = "password_hash")
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();

    public void addTweet(Tweet tweet){
        if(tweet == null) {
            throw new IllegalArgumentException("Tweet boş olamaz.");
        }
        if(tweet.getUser() != this) tweet.setUser(this);
        if(!tweets.contains(tweet)) tweets.add(tweet);
    }

    public void removeTweet(Tweet tweet){
        tweets.remove(tweet);
    }
    public List<Tweet> getTweets(){
        return Collections.unmodifiableList(this.tweets);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Retweet> retweets = new HashSet<>();

    public void addRetweet(Retweet retweet){
        if(retweet == null) {
            throw new IllegalArgumentException("Retweet boş olamaz.");
        }
        if(retweet.getUser() != this) retweet.setUser(this);
        retweets.add(retweet);
    }
    public void removeRetweet(Retweet retweet){
        retweets.remove(retweet);
    }
    public Set<Retweet> getRetweets(){
        return Collections.unmodifiableSet(this.retweets);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();

    public void addLike(Like like){
        if(like == null) {
            throw new IllegalArgumentException("Like boş olamaz.");
        }
        if(like.getUser() != this) like.setUser(this);
        likes.add(like);
    }
    public void removeLike(Like like){
        likes.remove(like);
    }
    public Set<Like> getLikes(){
        return Collections.unmodifiableSet(this.likes);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        if(comment == null) {
            throw new IllegalArgumentException("Yorum boş olamaz.");
        }
        if(comment.getUser() != this) comment.setUser(this);
        if(!comments.contains(comment)) comments.add(comment);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            schema = "twitter_schema",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();

    public void removeComment(Comment comment){
        comments.remove(comment);
    }
    public List<Comment> getComments(){
        return Collections.unmodifiableList(this.comments);
    }

    @Override
    public boolean equals(Object obj) {
       if(obj == this)
           return true;
       if(obj == null || obj.getClass() != getClass())
           return false;
       User user = (User) obj;
       return user.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
