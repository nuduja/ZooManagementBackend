package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimalRepository extends MongoRepository<Animal, String> {
    Animal findByName(String name);

    boolean existsByName(String name);

    String deleteByName(String name);
}
