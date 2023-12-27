package com.mappy.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationserviceApplication {

	public static void main(String[] args) {
		System.setProperty("application.properties", "authentication-service");
		SpringApplication.run(AuthenticationserviceApplication.class, args);
	}

}
