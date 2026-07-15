package com.workintech.twitter.clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Table(name = "role", schema = "twitter_schema")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role  implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=50)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(obj == null || obj.getClass() != getClass())
            return false;

        Role role = (Role)obj;

        return role.getId().equals(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
