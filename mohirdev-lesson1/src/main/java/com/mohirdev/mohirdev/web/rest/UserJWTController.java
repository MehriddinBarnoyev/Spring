package com.mohirdev.mohirdev.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohirdev.mohirdev.security.TokenProvider;

import com.mohirdev.mohirdev.web.rest.vm.LoginVM;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public UserJWTController(AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken>authorize(@RequestBody LoginVM loginVM ){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getWorkername(),
                loginVM.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate( usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication );
        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        return new ResponseEntity<>(new JwtToken(jwt), httpHeaders, HttpStatus.OK);

    }
    static class JwtToken{
        private String token;

        public JwtToken(String token) {
            this.token = token;
        }

        @JsonProperty("jwt_token")
        public String getToken() {
            return token;
        }
    }
}

















