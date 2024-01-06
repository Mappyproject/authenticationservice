package com.mappy.authservice.controller;

import com.mappy.authservice.dto.AccountAuthDto;
import com.mappy.authservice.dto.AccountDto;
import com.mappy.authservice.dto.AuthRequest;
import com.mappy.authservice.model.UserCredential;
import com.mappy.authservice.publisher.RabbitMQJsonProducer;
import com.mappy.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    private RabbitMQJsonProducer jsonProducer;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, RabbitMQJsonProducer jsonProducer) {
        this.authService = authService;
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody AccountDto accountDto) {
        jsonProducer.sendJsonMessage(accountDto);
        return ResponseEntity.ok("Account data sent to RabbitMQ ...");
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody AccountAuthDto accountAuthDto) {
        AccountDto accountDto = new AccountDto(accountAuthDto.getName(), accountAuthDto.getSurname(), accountAuthDto.getPhoneNumber(), accountAuthDto.getBirthDate());
        sendJsonMessage(accountDto);
        UserCredential user = new UserCredential(accountAuthDto.getUsername(), accountAuthDto.getEmail(), accountAuthDto.getPassword());
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
