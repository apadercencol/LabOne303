package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeekerServices {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Retrieve all seekers
    public List<Seeker> getAllSeekers() {
        String sql = "SELECT * FROM Seeker";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seeker.class));
    }

    // Find seekers by blood group
    public List<Seeker> findSeekersByBloodGroup(String bloodGroup) {
        String sql = "SELECT * FROM Seeker WHERE bloodGroup = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seeker.class), bloodGroup);
    }

    // Find seeker by firstName and lastName
    public List<Seeker> findSeekersByName(String firstName, String lastName) {
        String sql = "SELECT * FROM Seeker WHERE firstName = ? AND lastName = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seeker.class), firstName, lastName);
    }

    // Save a new seeker
    public int saveSeeker(Seeker seeker) {
        String sql = "INSERT INTO Seeker (firstName, lastName, age, bloodGroup, city, phone) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, seeker.getFirstName(), seeker.getLastName(), seeker.getAge(), seeker.getBloodGroup(), seeker.getCity(), seeker.getPhone());
    }

    // Update an existing seeker, identified by originalFirstName and originalLastName
    public int updateSeeker(Seeker seeker, String originalFirstName, String originalLastName) {
        String sql = "UPDATE Seeker SET firstName = ?, lastName = ?, age = ?, bloodGroup = ?, city = ?, phone = ? WHERE firstName = ? AND lastName = ?";
        return jdbcTemplate.update(sql, seeker.getFirstName(), seeker.getLastName(), seeker.getAge(), seeker.getBloodGroup(), seeker.getCity(), seeker.getPhone(), originalFirstName, originalLastName);
    }

    // Delete a seeker by firstName and lastName
    public int deleteSeeker(String firstName, String lastName) {
        String sql = "DELETE FROM Seeker WHERE firstName = ? AND lastName = ?";
        return jdbcTemplate.update(sql, firstName, lastName);
    }
}
