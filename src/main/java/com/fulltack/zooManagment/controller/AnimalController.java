package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.AnimalRequest;
import com.fulltack.zooManagment.exception.AnimalNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/animal")
public class AnimalController {

    @Autowired
    private AnimalService service;

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimal() {
        return ResponseEntity.ok(service.getAllAnimals());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Animal> getAnimal(@PathVariable String name) {
        try {
            return ResponseEntity.ok(service.getAnimalByName(name));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bySpeciesId/{animalSpeciesId}")
    public ResponseEntity<List<Animal>> getAnimalsBySpeciesId(@PathVariable String animalSpeciesId) {
        try {
            List<Animal> animals = service.getAnimalsBySpeciesId(animalSpeciesId);

            if (animals.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(animals);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //TODO:Do
//    @PutMapping
//    public ResponseEntity<String> updateTicket(@RequestBody AnimalRequest animalRequest) {
//        try {
//            return ResponseEntity.ok(service.updateAnimal(animalRequest));
//        } catch (ServiceException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @PostMapping("/createAnimal")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addAnimal(@RequestBody AnimalRequest animalRequest) {
        try {
            return ResponseEntity.ok(service.addAnimal(animalRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteAnimalByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(service.deleteAnimalByName(name));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/searchAnimal")
    public List<Animal> searchTickets(
            @RequestParam(required = false) String animalId,
            @RequestParam(required = false) String animalSpeciesId,
            @RequestParam(required = false) String name) {
        return service.searchAnimals(animalId, animalSpeciesId, name);
    }
}