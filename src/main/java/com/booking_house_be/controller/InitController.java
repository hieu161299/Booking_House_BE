package com.booking_house_be.controller;

import com.booking_house_be.service.IInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/init")
public class InitController {
    @Autowired
    private IInitService initService;
    @Autowired
    @PostMapping
    public ResponseEntity<?> init(){
        try {
            initService.init();
            return ResponseEntity.ok("Insert data successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}