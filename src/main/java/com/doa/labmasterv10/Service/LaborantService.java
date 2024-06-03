package com.doa.labmasterv10.Service;

import com.doa.labmasterv10.Entities.Laborant;
import com.doa.labmasterv10.Entities.Report;
import com.doa.labmasterv10.Repository.LaborantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaborantService {

    @Autowired
    private LaborantRepository laborantRepository;

    public List<Laborant> getAllLaborants() {
        return laborantRepository.findAll();
    }

    public Optional<Laborant> getLaborantById(Long id) {
        return laborantRepository.findById(id);
    }

    public Laborant saveLaborant(Laborant laborant) {
        validateHospitalID(laborant.getHospitalID(), laborant.getId());
        return laborantRepository.save(laborant);
    }

    public void deleteLaborant(Long id) {
        laborantRepository.deleteById(id);
    }

    public Laborant updateLaborant(Laborant laborant) {
        validateHospitalID(laborant.getHospitalID(), laborant.getId());
        Optional<Laborant> existingLaborantOpt = laborantRepository.findById(laborant.getId());
        if (existingLaborantOpt.isPresent()) {
            Laborant existingLaborant = existingLaborantOpt.get();

            existingLaborant.setHospitalID(laborant.getHospitalID());
            existingLaborant.setLaborantfirstName(laborant.getLaborantfirstName());
            existingLaborant.setLaborantlastName(laborant.getLaborantlastName());

            existingLaborant.getRaporlar().clear();
            for (Report newReport : laborant.getRaporlar()) {
                newReport.setLaborant(existingLaborant);
                existingLaborant.getRaporlar().add(newReport);
            }

            return laborantRepository.save(existingLaborant);
        } else {
            throw new IllegalArgumentException("Laborant with id " + laborant.getId() + " not found");
        }
    }

    private void validateHospitalID(Long hospitalID, Long laborantId) {
        Optional<Laborant> Laborant = laborantRepository.findByHospitalID(hospitalID);
        if (Laborant.isPresent() && !Laborant.get().getId().equals(laborantId)) {
            throw new IllegalArgumentException(hospitalID + " numarası ile kayıtlı başka bir laborant mevcut.");
        }
    }
}
