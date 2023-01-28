package com.example.visit.api;

import com.example.visit.model.User;
import com.example.visit.model.Visit;
import com.example.visit.model.WorkTime;
import com.example.visit.repo.UserRepo;
import com.example.visit.repo.VisitRepo;
import com.example.visit.repo.WorkTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reservation")
public class VisitController {
    @Autowired
    private WorkTimeRepo workTimeRepo;

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private UserRepo userRepo;


    @PostMapping("/")
    public ResponseEntity<?> reserve(@RequestBody Date date, Principal principal) {
        try {
            WorkTime workTime = workTimeRepo.findAll().stream().findAny()
                    .orElseThrow(()->{return new Exception("work time not set!");});
            if ( date.equals(workTime.getStart()) ||
                    (date.after(workTime.getStart()) && date.after(workTime.getEnd()))
            ) {
                if( (date.getTime()-workTime.getStart().getTime()) % workTime.getPeriod().getTime() == 0){
                    if (!visitRepo.existsByStart(date)){
                        User user = userRepo.findByUsername(principal.getName())
                                .orElseThrow(()->{return new Exception("User not found!");});
                        Visit visit = new Visit(date,user);
                        visitRepo.save(visit);
                        return ResponseEntity.ok(visit);
                    }
                    return ResponseEntity.badRequest().body("this time has been taken!");
                }
                return ResponseEntity.badRequest().body("bad time!");
            }
            return ResponseEntity.badRequest().body("out of work time range!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        Collection<Visit> visits = visitRepo.findAll();
        return ResponseEntity.ok(visits);
    }
}
