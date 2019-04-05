package com.example.sharpcodes.apisignaturesample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServiceController {
  
  
  @GetMapping("/hello")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("hello");
  }
  
}
