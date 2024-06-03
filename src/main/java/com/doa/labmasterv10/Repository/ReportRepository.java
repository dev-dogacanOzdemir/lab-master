package com.doa.labmasterv10.Repository;

import com.doa.labmasterv10.Entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    List<Report> findByPatientFirstNameContainsOrPatientLastNameContainsOrPatientTCContainsOrLaborant_LaborantfirstNameContainsOrLaborant_LaborantlastNameContains(
            String patientFirstName, String patientLastName, String patientTC, String laborantFirstName, String laborantLastName);
    List<Report> findAllByOrderByReportDateDesc();
    boolean existsByPatientTC(String patientTC);
}
