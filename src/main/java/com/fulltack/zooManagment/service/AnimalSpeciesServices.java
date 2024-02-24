package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.AnimalSpecies;
import com.fulltack.zooManagment.repository.AnimalSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalSpeciesServices {
    @Autowired
    private AnimalSpeciesRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<AnimalSpecies>> getAllAnimalSpecies() {
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception e) {
            System.out.println("Error occurred while fetching AnimalSpecies" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    public ResponseEntity<AnimalSpecies> getAnimalSpecies(String name) {
        try {
            return ResponseEntity.ok(repository.findByName(name));
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }

    public ResponseEntity<String> addAnimalSpecies(AnimalSpecies animalSpecies) {
        try {
            if (!repository.existsByName(animalSpecies.getName().trim())) {
                repository.save(animalSpecies);
                return ResponseEntity.status(HttpStatus.CREATED).body("User " + animalSpecies.getName() + " Saved Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username " + animalSpecies.getName() + " Already Exists");
            }

        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding an animalSpecies", e);
        }
    }

    public ResponseEntity<String> deleteAnimalSpecies(String name) {
        try {
            if (repository.existsByName(name)) {
                repository.deleteByName(name);
                return ResponseEntity.ok(name + " AnimalSpecies Deleted Successfully");
            } else {
                return ResponseEntity.ok(name + " AnimalSpecies Does not exists");
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting AnimalSpecies", e);
        }
    }
}