package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodStockServices {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Retrieve all blood stocks
    public List<BloodStock> getAllBloodStocks() {
        String sql = "SELECT * FROM BloodStock";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BloodStock.class));
    }

    // Find blood stock by blood group and best before date
    public List<BloodStock> findBloodStockByGroupAndDate(String bloodGroup, String bestBefore) {
        String sql = "SELECT * FROM BloodStock WHERE bloodGroup = ? AND bestBefore = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BloodStock.class), bloodGroup, bestBefore);
    }

    // Save a new blood stock
    public int saveBloodStock(BloodStock bloodStock) {
        String sql = "INSERT INTO BloodStock (bloodGroup, quantity, bestBefore, status) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, bloodStock.getBloodGroup(), bloodStock.getQuantity(), bloodStock.getBestBefore(), bloodStock.getStatus());
    }

    // Update an existing blood stock, identified by original bloodGroup and bestBefore
    public int updateBloodStock(BloodStock bloodStock, String originalBloodGroup, String originalBestBefore) {
        String sql = "UPDATE BloodStock SET bloodGroup = ?, quantity = ?, bestBefore = ?, status = ? WHERE bloodGroup = ? AND bestBefore = ?";
        return jdbcTemplate.update(sql, bloodStock.getBloodGroup(), bloodStock.getQuantity(), bloodStock.getBestBefore(), bloodStock.getStatus(), originalBloodGroup, originalBestBefore);
    }

    // Delete a blood stock by bloodGroup and bestBefore
    public int deleteBloodStock(String bloodGroup, String bestBefore) {
        String sql = "DELETE FROM BloodStock WHERE bloodGroup = ? AND bestBefore = ?";
        return jdbcTemplate.update(sql, bloodGroup, bestBefore);
    }
}
