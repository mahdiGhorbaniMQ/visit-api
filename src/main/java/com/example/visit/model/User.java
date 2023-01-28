package com.example.visit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Id
    private long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter @ManyToMany
    private Collection<Authority> authorities = new HashSet<>();

    public User() {}
    public User(
            String name,
            String username,
            String password,
            Set<Authority> authorities
    ) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
