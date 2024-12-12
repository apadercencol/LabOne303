package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bloodbanks")
@CrossOrigin(origins = "http://localhost:3000")
public class BloodBankController {

    @Autowired
    private BloodBankRepository bloodBankRepository;

    // Default endpoint to fetch all blood bank records
    @GetMapping
    public ResponseEntity<List<BloodBank>> getAllBloodBanks() {
        List<BloodBank> bloodBanks = bloodBankRepository.findAll();
        if (bloodBanks.isEmpty()) {
            System.out.println("No blood bank records found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("Fetched Blood Banks: " + bloodBanks);
        return ResponseEntity.ok(bloodBanks);
    }

    // Optional endpoint to fetch all records using "/all"
    @GetMapping("/all")
    public ResponseEntity<List<BloodBank>> getAllBloodBanksAlias() {
        List<BloodBank> bloodBanks = bloodBankRepository.findAll();
        if (bloodBanks.isEmpty()) {
            System.out.println("No blood bank records found on '/all'.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        System.out.println("Fetched Blood Banks from '/all': " + bloodBanks);
        return ResponseEntity.ok(bloodBanks);
    }

    // Debug endpoint to check if the service is running
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("BloodBankController is running!");
    }

    // Update a blood bank record
    @PutMapping("/update")
    public ResponseEntity<String> updateBloodBank(@RequestBody BloodBank updatedBloodBank) {
        Optional<BloodBank> existingBloodBank = bloodBankRepository.findById(updatedBloodBank.getBloodbankname());
        if (existingBloodBank.isPresent()) {
            bloodBankRepository.save(updatedBloodBank);
            System.out.println("Updated Blood Bank: " + updatedBloodBank);
            return ResponseEntity.ok("Blood Bank updated successfully.");
        } else {
            System.out.println("Blood Bank not found: " + updatedBloodBank.getBloodbankname());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blood Bank not found.");
        }
    }

    // Add a new blood bank record
    @PostMapping("/add")
    public ResponseEntity<String> addBloodBank(@RequestBody BloodBank newBloodBank) {
        if (bloodBankRepository.existsById(newBloodBank.getBloodbankname())) {
            System.out.println("Blood Bank already exists: " + newBloodBank.getBloodbankname());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Blood Bank already exists.");
        }
        bloodBankRepository.save(newBloodBank);
        System.out.println("Added New Blood Bank: " + newBloodBank);
        return ResponseEntity.status(HttpStatus.CREATED).body("Blood Bank added successfully.");
    }
}
