package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.AnimalSpecies;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalSpeciesRepository extends MongoRepository<AnimalSpecies, String> {
    AnimalSpecies findByAnimalSpeciesName(String name);

    AnimalSpecies findByAnimalSpeciesId(String animalSpeciesId);

    boolean existsByAnimalSpeciesName(String name);

    String deleteByAnimalSpeciesName(String name);
}