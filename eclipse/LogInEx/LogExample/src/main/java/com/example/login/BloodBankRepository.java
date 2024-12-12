package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, String> {
    // JpaRepository provides default methods for basic CRUD operations.
}
