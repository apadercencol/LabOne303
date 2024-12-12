package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bloodstock")
@CrossOrigin(origins = "http://localhost:3000")
public class BloodStockController {

    @Autowired
    private BloodStockRepository bloodStockRepository;

    // Endpoint to get all blood stock records
    @GetMapping
    public ResponseEntity<List<BloodStock>> getAllBloodStock() {
        List<BloodStock> bloodStockList = bloodStockRepository.findAll();
        if (bloodStockList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(bloodStockList);
    }

    // Endpoint to update a blood stock record
    @PutMapping("/update")
    public ResponseEntity<String> updateBloodStock(@RequestBody BloodStock updatedStock) {
        Optional<BloodStock> existingStock = bloodStockRepository.findById(updatedStock.getBloodGroup());
        if (existingStock.isPresent()) {
            BloodStock stock = existingStock.get();
            stock.setQuantity(updatedStock.getQuantity());
            stock.setBestBefore(updatedStock.getBestBefore());
            stock.setStatus(updatedStock.getStatus());
            bloodStockRepository.save(stock);
            return ResponseEntity.ok("Blood Stock updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blood Stock not found.");
        }
    }

    // Endpoint to add a new blood stock record
    @PostMapping("/add")
    public ResponseEntity<String> addBloodStock(@RequestBody BloodStock newStock) {
        try {
            // Check if the blood group already exists
            Optional<BloodStock> existingStock = bloodStockRepository.findById(newStock.getBloodGroup());
            if (existingStock.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Blood stock for this blood group already exists.");
            }
            bloodStockRepository.save(newStock);
            return ResponseEntity.ok("Blood Stock added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding Blood Stock.");
        }
    }
}
