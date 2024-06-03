package com.doa.labmasterv10.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Laborant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Min(value = 1000000, message = "Hastane ID 7 karakter olmalı")
    @Max(value = 9999999, message = "Hastane ID 7 karakter olmalı")
    private Long hospitalID;

    private String laborantfirstName;
    private String laborantlastName;
    @Column(length = 7)

    @OneToMany(mappedBy = "laborant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> raporlar= new ArrayList<>();
}

