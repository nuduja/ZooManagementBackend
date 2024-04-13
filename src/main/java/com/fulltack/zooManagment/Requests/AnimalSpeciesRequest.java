package com.fulltack.zooManagment.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AnimalSpeciesRequest {
    @NotNull
    private String animalSpeciesName;
    private String taxonomy_kingdom;
    private String taxonomy_scientific_name;
    private String characteristics_group_behavior;
    private String characteristics_diet;
    private String characteristics_skin_type;
    private String characteristics_top_speed;
    private String characteristics_lifespan;
    private String characteristics_weight;
}