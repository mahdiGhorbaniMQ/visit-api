package com.example.visit.repo;

import com.example.visit.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepo extends JpaRepository<Authority,Long> {
    Optional<Authority> findByAuthority(String authority);
}
