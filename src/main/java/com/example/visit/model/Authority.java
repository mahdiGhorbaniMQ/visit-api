package com.example.visit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Authority implements GrantedAuthority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Id
    private long id;

    @Getter @Setter
    private String authority;


    public Authority(){}
    public Authority(String authority){
        this.authority = authority;
    }
}
