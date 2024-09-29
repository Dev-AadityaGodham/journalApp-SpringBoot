package com.tech.journalApp.HealthCheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("/health")
    public ResponseEntity<?> HealthCheck(){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
