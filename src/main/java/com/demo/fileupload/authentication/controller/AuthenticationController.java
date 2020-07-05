package com.demo.fileupload.authentication.controller;

import com.demo.fileupload.authentication.ApiUserDetailsService;
import com.demo.fileupload.authentication.AuthenticationRequest;
import com.demo.fileupload.authentication.AuthenticationResponse;
import com.demo.fileupload.authentication.token.JwtTokenProvider;
import com.demo.fileupload.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ApiUserDetailsService apiUserDetailsService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUserName(), authenticationRequest.getPassword()));

        if(authentication.isAuthenticated()) {
            User loggedInUer = ((User)authentication.getPrincipal());
            final String token = jwtTokenProvider.createToken(loggedInUer.getUsername(), loggedInUer.getAuthorities());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).build();
        }

    }
}