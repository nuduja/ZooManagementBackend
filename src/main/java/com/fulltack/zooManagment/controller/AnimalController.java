package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService service;

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimal() {
        return service.getAllAnimals();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Animal> getAnimal(@PathVariable String name) {
        return service.getAnimal(name);
    }

    @PostMapping("/createAnimal")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addAnimal(@RequestBody Animal animal) {
        return service.addAnimal(animal);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteAnimal(@PathVariable String name) {
        return service.deleteAnimal(name);
    }
}