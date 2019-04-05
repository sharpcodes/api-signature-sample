package com.example.sharpcodes.apisignaturesample.filter;

import com.example.sharpcodes.apisignaturesample.security.SignatureUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class SignatureFilter implements Filter {
  
  @Autowired
  private SignatureUtility signatureUtility;
  
  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
    
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    log.info(httpRequest.getPathInfo());
    try {
      if (!signatureUtility.validate(httpRequest)) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Signature");
      } else {
        filterChain.doFilter(servletRequest, servletResponse);
      }
    } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
      log.error("Exception when validating the signature ", ex);
    }
    
    
  }
}
