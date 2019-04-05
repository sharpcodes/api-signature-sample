package com.example.sharpcodes.apisignaturesample.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SecretService {
  
  Map<String, String> clientSecrets = new HashMap<>();
  
  public Optional<String> getClientkey(String clientId) {
    /*
      Only for demonstration. Do not do this.
      This can be replaced a service that looks up a cache or DB to resolve the secret that was provisioned for the client
     */
    return Optional.ofNullable(clientSecrets.getOrDefault(clientId, "secret"));
  }
  
  
}
