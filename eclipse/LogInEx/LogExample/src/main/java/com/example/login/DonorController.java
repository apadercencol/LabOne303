package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "http://localhost:3000")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodBankRepository bloodBankRepository;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginDonor(@RequestBody Donor loginRequest) {
        Optional<Donor> donor = donorRepository.findByUserName(loginRequest.getUserName());
        if (donor.isPresent() && donor.get().getPassword().equals(loginRequest.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("redirect", "/profile");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password."));
        }
    }

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerDonor(@RequestBody Donor donorRequest) {
        if (donorRequest.getUserName() == null || donorRequest.getUserName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required.");
        }
        if (donorRequest.getPassword() == null || donorRequest.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }
        if (donorRepository.findByUserName(donorRequest.getUserName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }
        donorRepository.save(donorRequest);
        return ResponseEntity.ok("Registration successful!");
    }

    // View profile by username
    @GetMapping("/profile/{username}")
    public ResponseEntity<Map<String, Object>> getDonorProfileSummary(@PathVariable String username) {
        Optional<Donor> donorOpt = donorRepository.findByUserNameWithBloodBank(username);
        if (donorOpt.isPresent()) {
            Donor donor = donorOpt.get();
            Map<String, Object> profileSummary = new HashMap<>();
            profileSummary.put("firstname", donor.getFirstname());
            profileSummary.put("lastname", donor.getLastname());
            profileSummary.put("phone", donor.getPhone());
            profileSummary.put("age_or_dob", donor.getAge_or_dob());
            profileSummary.put("gender", donor.getGender());
            profileSummary.put("city", donor.getCity());
            profileSummary.put("donorid", donor.getDonorid());
            profileSummary.put("bloodgroup", donor.getBloodgroup());
            profileSummary.put("bloodbankname",
                    donor.getBloodBank() != null ? donor.getBloodBank().getBloodbankname() : null);
            profileSummary.put("last_donation", donor.getLast_donation());
            profileSummary.put("scheduled_donation", donor.getScheduled_donation());
            return ResponseEntity.ok(profileSummary);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Update donor information
    @PutMapping("/update")
    public ResponseEntity<String> updateDonor(@RequestBody Map<String, Object> requestData) {
        Long donorId = requestData.containsKey("donorid") ? Long.valueOf(requestData.get("donorid").toString()) : null;
        String bloodBankName = requestData.containsKey("bloodbankname") ? requestData.get("bloodbankname").toString() : null;

        if (donorId == null) {
            return ResponseEntity.badRequest().body("Donor ID is required.");
        }

        Optional<Donor> donorOpt = donorRepository.findById(donorId);
        if (donorOpt.isPresent()) {
            Donor existingDonor = donorOpt.get();

            // Update fields dynamically
            if (requestData.containsKey("firstname")) existingDonor.setFirstname(requestData.get("firstname").toString());
            if (requestData.containsKey("lastname")) existingDonor.setLastname(requestData.get("lastname").toString());
            if (requestData.containsKey("phone")) existingDonor.setPhone(requestData.get("phone").toString());
            if (requestData.containsKey("age_or_dob")) existingDonor.setAge_or_dob(requestData.get("age_or_dob").toString());
            if (requestData.containsKey("gender")) existingDonor.setGender(requestData.get("gender").toString());
            if (requestData.containsKey("city")) existingDonor.setCity(requestData.get("city").toString());
            if (requestData.containsKey("bloodgroup")) existingDonor.setBloodgroup(requestData.get("bloodgroup").toString());
            if (requestData.containsKey("scheduled_donation")) {
                existingDonor.setLast_donation(existingDonor.getScheduled_donation());
                existingDonor.setScheduled_donation(requestData.get("scheduled_donation").toString());
            }

            // Handle Blood Bank association
            if (bloodBankName != null) {
                Optional<BloodBank> bloodBankOpt = bloodBankRepository.findById(bloodBankName);
                if (bloodBankOpt.isPresent()) {
                    existingDonor.setBloodBank(bloodBankOpt.get());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Blood Bank '" + bloodBankName + "' not found.");
                }
            }

            donorRepository.save(existingDonor);
            return ResponseEntity.ok("Donor updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Donor not found.");
        }
    }

    // Full profile retrieval
    @GetMapping("/full-profile/{username}")
    public ResponseEntity<Donor> getFullProfile(@PathVariable String username) {
        Optional<Donor> donorOpt = donorRepository.findByUserNameWithBloodBank(username);
        return donorOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
