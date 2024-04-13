package com.fulltack.zooManagment.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AnimalRequest {
    @NotNull
    private String animalSpeciesId;
    private String animalSpeciesName;
    @NotNull
    private String name;
    private String enclosureId;
    private LocalDate birthDate;
    private String birthCountry;
    private String description;
}