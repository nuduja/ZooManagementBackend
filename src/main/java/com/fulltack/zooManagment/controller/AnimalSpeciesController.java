package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.model.AnimalSpecies;
import com.fulltack.zooManagment.service.AnimalSpeciesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animalspecies")
public class AnimalSpeciesController {

    @Autowired
    private AnimalSpeciesServices service;

    @GetMapping
    public ResponseEntity<List<AnimalSpecies>> getAllAnimalSpecies() {
        return service.getAllAnimalSpecies();
    }

    @GetMapping("/{name}")
    public ResponseEntity<AnimalSpecies> getAnimalSpecies(@PathVariable String name) {
        return service.getAnimalSpecies(name);
    }

    @PostMapping("/createAnimalSpecies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addAnimalSpecies(@RequestBody AnimalSpecies animalSpecies) {
        return service.addAnimalSpecies(animalSpecies);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteAnimalSpecies(@PathVariable String name) {
        return service.deleteAnimalSpecies(name);
    }
}