package com.mappy.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAuthDto {
    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private Date birthDate;
}
