package com.example.sharpcodes.apisignaturesample.security;

import com.example.sharpcodes.apisignaturesample.service.SecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Component
@Slf4j
public class SignatureUtility {
  
  
  @Autowired
  private SecretService secretService;
  
  
  public boolean validate(HttpServletRequest httpServletRequest) throws InvalidKeyException, NoSuchAlgorithmException {
    
    String path = httpServletRequest.getPathInfo();
    
    String clientId = httpServletRequest.getParameter("clientId");
    Optional<String> keyString = secretService.getClientkey(clientId);
    
    if (keyString.isPresent()) {
      // Assumes that signature is at the very end of the query string
      String resource = path + '?' + httpServletRequest.getQueryString().split("&signature=")[0];
      
      String signatureValue = httpServletRequest.getParameter("signature");
      
      // Convert the key from 'web safe' base 64 to binary
      String keyStringBinary = keyString.get().replace('-', '+').replace('_', '/');
      
      // Base64 is JDK 1.8 only - older versions may need to use Apache Commons or similar.
      byte[] key = Base64.getDecoder().decode(keyStringBinary);
      
      // Get an HMAC-SHA1 signing key from the raw key bytes
      SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");
      
      // Get an HMAC-SHA1 Mac instance and initialize it with the HMAC-SHA1 key
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(sha1Key);
      
      // compute the binary signature for the request
      byte[] sigBytes = mac.doFinal(resource.getBytes());
      
      // base 64 encode the binary signature
      // Base64 is JDK 1.8 only - older versions may need to use Apache Commons or similar.
      String signature = Base64.getEncoder().encodeToString(sigBytes);
      
      // convert the signature to 'web safe' base 64
      signature = signature.replace('+', '-');
      signature = signature.replace('/', '_');
      
      if (signatureValue.equals(signature)) {
        return true;
      }
    }
    return false;
  }
}
