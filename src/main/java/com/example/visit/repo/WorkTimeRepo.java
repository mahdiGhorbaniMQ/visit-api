package com.example.visit.repo;

import com.example.visit.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTimeRepo extends JpaRepository<WorkTime,Long> {
}
