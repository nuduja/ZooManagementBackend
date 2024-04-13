package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    Animal findByName(String name);

    List<Animal> findByAnimalSpeciesId(String animalSpeciesId);

    boolean existsByName(String name);

    String deleteByName(String name);
}