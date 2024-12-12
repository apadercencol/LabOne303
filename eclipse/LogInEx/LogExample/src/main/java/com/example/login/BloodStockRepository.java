package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodStockRepository extends JpaRepository<BloodStock, String> {
    // JpaRepository provides default CRUD operations.
}
