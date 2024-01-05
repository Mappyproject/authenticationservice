package com.mappy.authenticationservice.controller;

import com.mappy.authenticationservice.dto.AccountAuthDto;
import com.mappy.authenticationservice.dto.AccountDto;
import com.mappy.authenticationservice.dto.AuthRequest;
import com.mappy.authenticationservice.model.UserCredential;
import com.mappy.authenticationservice.publisher.RabbitMQJsonProducer;
import com.mappy.authenticationservice.service.AuthService;
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

    @Autowired
    private AuthenticationManager authenticationManager;
    private RabbitMQJsonProducer jsonProducer;

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
