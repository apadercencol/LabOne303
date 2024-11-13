package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodBankServices {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Retrieve all blood banks
    public List<BloodBank> getAllBloodBanks() {
        String sql = "SELECT * FROM BloodBank";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BloodBank.class));
    }

    // Find blood bank by name and city
    public List<BloodBank> findBloodBankByNameAndCity(String bloodbankName, String city) {
        String sql = "SELECT * FROM BloodBank WHERE bloodbankName = ? AND city = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BloodBank.class), bloodbankName, city);
    }

    // Save a new blood bank
    public int saveBloodBank(BloodBank bloodBank) {
        String sql = "INSERT INTO BloodBank (bloodbankName, address, city, phone, email) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, bloodBank.getBloodbankName(), bloodBank.getAddress(), bloodBank.getCity(), bloodBank.getPhone(), bloodBank.getEmail());
    }

    // Update an existing blood bank, identified by original bloodbankName and city
    public int updateBloodBank(BloodBank bloodBank, String originalBloodbankName, String originalCity) {
        String sql = "UPDATE BloodBank SET bloodbankName = ?, address = ?, city = ?, phone = ?, email = ? WHERE bloodbankName = ? AND city = ?";
        return jdbcTemplate.update(sql, bloodBank.getBloodbankName(), bloodBank.getAddress(), bloodBank.getCity(), bloodBank.getPhone(), bloodBank.getEmail(), originalBloodbankName, originalCity);
    }

    // Delete a blood bank by name and city
    public int deleteBloodBank(String bloodbankName, String city) {
        String sql = "DELETE FROM BloodBank WHERE bloodbankName = ? AND city = ?";
        return jdbcTemplate.update(sql, bloodbankName, city);
    }
}
