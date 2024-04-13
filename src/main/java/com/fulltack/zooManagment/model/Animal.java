package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    private String id;
    @Indexed(unique = true)
    private String animalId;
    private String animalSpeciesId;
    private String animalSpeciesName;
    private String name;
    private String enclosureId;
    private LocalDate birthDate;
    private String birthCountry;
    private String description;
}
