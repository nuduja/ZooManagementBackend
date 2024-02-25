package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

    public ResponseEntity<List<Animal>> getAllAnimals() {
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception e) {
            System.out.println("Error occurred while fetching Animal" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    public ResponseEntity<Animal> getAnimal(String name) {
        try {
            return ResponseEntity.ok(repository.findByName(name));
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific animal", e);
        }
    }

    public ResponseEntity<String> addAnimal(Animal animal) {
        try {
            if (!repository.existsByName(animal.getName().trim())) {
                repository.save(animal);
                return ResponseEntity.status(HttpStatus.CREATED).body("Animal " + animal.getName() + " Saved Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username " + animal.getName() + " Already Exists");
            }

        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding an animal", e);
        }
    }

    public ResponseEntity<String> deleteAnimal(String name) {
        try {
            if (repository.existsByName(name)) {
                repository.deleteByName(name);
                return ResponseEntity.ok(name + " Animal Deleted Successfully");
            } else {
                return ResponseEntity.ok(name + " Animal Does not exists");
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting Animal", e);
        }
    }
}
