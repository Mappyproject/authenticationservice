package com.mappy.authenticationservice.repository;

import com.mappy.authenticationservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByName(String username);
}
