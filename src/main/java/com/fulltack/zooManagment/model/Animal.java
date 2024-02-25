package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    private String id;
    private String animalSpecificId;
    private String animalSpeciesName;
    private String animalSpeciesId;
    private String name;
    private String enclosureId;
    private String age;
}
