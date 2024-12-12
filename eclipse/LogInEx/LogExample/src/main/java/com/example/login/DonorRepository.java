package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    /**
     * Find a donor by username.
     *
     * @param userName the username of the donor
     * @return Optional containing the donor if found
     */
    Optional<Donor> findByUserName(String userName);

    /**
     * Find a donor by ID, including the associated BloodBank entity.
     *
     * @param donorId the ID of the donor
     * @return Optional containing the donor with its BloodBank if found
     */
    @Query("SELECT d FROM Donor d LEFT JOIN FETCH d.bloodBank WHERE d.donorid = :donorid")
    Optional<Donor> findByIdWithBloodBank(@Param("donorid") Long donorId);

    /**
     * Find a donor by username, including the associated BloodBank entity.
     *
     * @param userName the username of the donor
     * @return Optional containing the donor with its BloodBank if found
     */
    @Query("SELECT d FROM Donor d LEFT JOIN FETCH d.bloodBank WHERE d.userName = :userName")
    Optional<Donor> findByUserNameWithBloodBank(@Param("userName") String userName);
}
