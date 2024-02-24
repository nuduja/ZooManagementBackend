package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.AnimalSpecies;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalSpeciesRepository extends MongoRepository<AnimalSpecies, String> {
    AnimalSpecies findByName(String name);

    boolean existsByName(String name);

    String deleteByName(String name);
}