package com.doa.labmasterv10.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patientFirstName;
    private String patientLastName;

    @NotBlank(message = "T.C. Kimlik Numarası boş bırakılamaz.")
    @Pattern(regexp = "\\d{11}", message = "T.C. Kimlik Numarası sadece rakamlardan ve 11 karakterden oluşmalıdır.")
    @Column(unique = true)
    private String patientTC;

    private String diagnoseTitle;
    private String diagnoseDetails;
    private LocalDate reportDate;

    @ManyToOne
    @JoinColumn(name = "laborant_id", referencedColumnName = "id")
    private Laborant laborant;

    private String reportImagePath;
}
