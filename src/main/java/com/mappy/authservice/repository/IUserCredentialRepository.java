package com.mappy.authservice.repository;

import com.mappy.authservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserCredentialRepository extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByName(String username);
}
