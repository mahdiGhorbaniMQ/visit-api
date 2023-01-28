package com.example.visit.api;

import com.example.visit.Message;
import com.example.visit.model.Authority;
import com.example.visit.model.User;
import com.example.visit.repo.AuthorityRepo;
import com.example.visit.repo.UserRepo;
import com.example.visit.security.jwt.JwtUtils;
import com.example.visit.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthorityRepo authorityRepo;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User payload) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userDetails.setJwt(jwt);
        return ResponseEntity.ok(userDetails);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User payload) {
        if (userRepo.existsByUsername(payload.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        Set<Authority> authorities = new HashSet<>();
        Optional<Authority> authority = authorityRepo.findByAuthority("ROLE_USER");
        authority.ifPresent(authorities::add);

        User user = new User(
                payload.getName(),
                payload.getUsername(),
                encoder.encode(payload.getPassword()),
                authorities
        );

        userRepo.save(user);
        return ResponseEntity.ok().build();
    }



//    @GetMapping("/test")
//    String test(){
//        Authority roleUser = new Authority("ROLE_USER");
//        Authority roleAdmin = new Authority("ROLE_ADMIN");
//        authorityRepo.save(roleUser);
//        authorityRepo.save(roleAdmin);
//        Set<Authority> authorities = new HashSet<>();
//        authorities.add(roleAdmin);
//        User admin = new User(
//                "admin",
//                "admin",
//                encoder.encode("admin"),
//                authorities
//        );
//
//        userRepo.save(admin);
//        return "ok";
//    }
}