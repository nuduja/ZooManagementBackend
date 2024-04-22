package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.AnimalSpeciesRequest;
import com.fulltack.zooManagment.exception.AnimalNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.AnimalSpecies;
import com.fulltack.zooManagment.service.AnimalSpeciesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/animalSpeciesId/{animalSpeciesId}")
    public ResponseEntity<AnimalSpecies> getAnimalSpeciesByAnimalSpeciesId(@PathVariable String animalSpeciesId) {
        try {
            return ResponseEntity.ok(service.getAnimalSpeciesByAnimalSpeciesId(animalSpeciesId));
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

    @DeleteMapping("/{animalSpeciesId}")
    public ResponseEntity<String> deleteAnimalSpeciesByAnimalSpeciesId(@PathVariable String animalSpeciesId) {
        try {
            return ResponseEntity.ok(service.deleteAnimalSpeciesByAnimalSpeciesId(animalSpeciesId));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/searchAnimalSpecies")
    public List<AnimalSpecies> searchAnimalSpecies(
            @RequestParam(required = false) String animalSpeciesId,
            @RequestParam(required = false) String animalSpeciesName) {
        return service.searchAnimalSpecies(animalSpeciesId, animalSpeciesName);
    }

    @PutMapping("/updatebyanimalspeciesid/{animalSpeciesId}")
    public String updateAnimalSpeciesIdByAnimalSpeciesId(@PathVariable String animalSpeciesId, @RequestBody Map<String, Object> updates) {
        service.updateAnimalSpeciesIdByAnimalSpeciesId(animalSpeciesId, updates);
        return "AnimalSpecies updated successfully";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> ticketsReport() {
        ByteArrayInputStream bis = service.generateAnimalSpeciesPDF();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=animalSpecies.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}