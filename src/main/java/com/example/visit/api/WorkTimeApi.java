package com.example.visit.api;

import com.example.visit.model.WorkTime;
import com.example.visit.repo.WorkTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/work-time")
public class WorkTimeApi {

    @Autowired
    private WorkTimeRepo workTimeRepo;



    @GetMapping("/")
    public ResponseEntity<?> getTime() {
        try {
            WorkTime workTime = workTimeRepo.findAll().stream().findAny().orElseThrow(Exception::new);
            return ResponseEntity.ok(workTime);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("time not set!");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> setTime(@RequestBody WorkTime payload) {
        try {
            WorkTime workTime = workTimeRepo.findAll().stream().findAny().orElseThrow(Exception::new);
            workTime.setStart(payload.getStart());
            workTime.setEnd(payload.getEnd());
            workTime.setPeriod(payload.getPeriod());
            workTimeRepo.save(workTime);
            return ResponseEntity.ok(workTime);
        } catch (Exception e) {
            WorkTime workTime = new WorkTime(
                    payload.getStart(),
                    payload.getEnd(),
                    payload.getPeriod()
            );
            workTimeRepo.save(workTime);
            return ResponseEntity.ok(workTime);
        }
    }
}