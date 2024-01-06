package com.mappy.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private Date birthDate;
}
