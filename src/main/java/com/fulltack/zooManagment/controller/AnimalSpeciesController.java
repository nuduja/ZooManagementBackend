package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.AnimalSpeciesRequest;
import com.fulltack.zooManagment.exception.AnimalNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.AnimalSpecies;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.service.AnimalSpeciesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/animalspecies")
public class AnimalSpeciesController {

    @Autowired
    private AnimalSpeciesServices service;

    @GetMapping
    public ResponseEntity<List<AnimalSpecies>> getAllAnimalSpecies() {
        return ResponseEntity.ok(service.getAllAnimalSpecies());
    }

    @GetMapping("/{name}")
    public ResponseEntity<AnimalSpecies> getAnimalSpecies(@PathVariable String name) {
        try {
            return ResponseEntity.ok(service.getAnimalSpecies(name));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createAnimalSpecies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addAnimalSpecies(@RequestBody AnimalSpeciesRequest animalSpeciesRequest) {
        try {
            return ResponseEntity.ok(service.addAnimalSpecies(animalSpeciesRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteAnimalSpecies(@PathVariable String name) {
        try {
            return ResponseEntity.ok(service.deleteAnimalSpecies(name));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public List<Ticket> searchTickets(
            @RequestParam(required = false) String animalSpeciesId,
            @RequestParam(required = false) String animalSpeciesName) {
        return service.searchAnimalSpecies(animalSpeciesId, animalSpeciesName);
    }
}