package com.example.visit.repo;

import com.example.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface VisitRepo extends JpaRepository<Visit,Long> {
    Boolean existsByStart(Date start);
}
