package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSpecies {
    @Id
    private String id;
    private String animalId;
    private String name;
    private String taxonomy_kingdom;
    private String taxonomy_scientific_name;
    private String characteristics_group_behavior;
    private String characteristics_diet;
    private String characteristics_skin_type;
    private String characteristics_top_speed;
    private String characteristics_lifespan;
    private String characteristics_weight;
}