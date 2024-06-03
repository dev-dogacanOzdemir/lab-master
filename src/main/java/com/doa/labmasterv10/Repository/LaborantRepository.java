package com.doa.labmasterv10.Repository;

import com.doa.labmasterv10.Entities.Laborant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaborantRepository extends JpaRepository<Laborant, Long> {
    Optional<Laborant> findByHospitalID(Long hospitalID);
}
