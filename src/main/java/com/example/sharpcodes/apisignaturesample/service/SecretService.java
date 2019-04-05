package com.example.sharpcodes.apisignaturesample.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SecretService {
  
  Map<String, String> clientSecrets = new HashMap<>();
  
  
  public Optional<String> getClientkey(String clientId) {
    //Only for demonstration. Do not do this
    return Optional.ofNullable(clientSecrets.getOrDefault(clientId, "secret"));
  }
  
  
}
