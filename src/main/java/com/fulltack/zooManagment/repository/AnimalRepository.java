package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    Animal findByAnimalId(String animalId);

    List<Animal> findByAnimalSpeciesId(String animalSpeciesId);

    boolean existsByAnimalId(String animalId);

    String deleteByAnimalId(String animalId);
}