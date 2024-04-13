package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.AnimalRequest;
import com.fulltack.zooManagment.Requests.AnimalSpeciesRequest;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.model.AnimalSpecies;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.AnimalSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnimalSpeciesServices {
    @Autowired
    private AnimalSpeciesRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public AnimalSpecies convertToAnimalSpecies(AnimalSpeciesRequest animalSpeciesRequest) {
        AnimalSpecies animalSpecies = new AnimalSpecies();
        animalSpecies.setAnimalSpeciesId(UUID.randomUUID().toString().split("-")[0]);
        animalSpecies.setAnimalSpeciesName(animalSpeciesRequest.getAnimalSpeciesName());
        animalSpecies.setTaxonomy_kingdom(animalSpeciesRequest.getTaxonomy_kingdom());
        animalSpecies.setTaxonomy_scientific_name(animalSpeciesRequest.getTaxonomy_scientific_name());
        animalSpecies.setCharacteristics_group_behavior(animalSpeciesRequest.getCharacteristics_group_behavior());
        animalSpecies.setCharacteristics_diet(animalSpeciesRequest.getCharacteristics_diet());
        animalSpecies.setCharacteristics_skin_type(animalSpeciesRequest.getCharacteristics_skin_type());
        animalSpecies.setCharacteristics_top_speed(animalSpeciesRequest.getCharacteristics_top_speed());
        animalSpecies.setCharacteristics_lifespan(animalSpeciesRequest.getCharacteristics_lifespan());
        animalSpecies.setCharacteristics_weight(animalSpeciesRequest.getCharacteristics_weight());
        return animalSpecies;
    }

    public List<AnimalSpecies> getAllAnimalSpecies() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Tickets", e);
        }
    }

    public AnimalSpecies getAnimalSpecies(String name) {
        try {
            return repository.findByAnimalSpeciesName(name);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }

    public String addAnimalSpecies(AnimalSpeciesRequest animalSpeciesRequest) {
        try {
            AnimalSpecies animalSpecies = convertToAnimalSpecies(animalSpeciesRequest);
            if (!repository.existsByAnimalSpeciesName(animalSpecies.getAnimalSpeciesId().trim())) {
                if (animalSpecies.getAnimalSpeciesId() == null || animalSpecies.getAnimalSpeciesName() == null) {
                    throw new IllegalArgumentException("Animal Species ID and Name must be valid.");
                }
                repository.save(animalSpecies);
                return "User " + animalSpecies.getAnimalSpeciesName() + " Saved Successfully";
            } else {
                return "Username " + animalSpecies.getAnimalSpeciesName() + " Already Exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding an animalSpecies", e);
        }
    }

    public String deleteAnimalSpecies(String name) {
        try {
            if (repository.existsByAnimalSpeciesName(name)) {
                repository.deleteByAnimalSpeciesName(name);
                return "AnimalSpecies Deleted Successfully";
            } else {
                return "AnimalSpecies Does not exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting AnimalSpecies", e);
        }
    }

    public List<Ticket> searchAnimalSpecies(String animalSpeciesId, String animalSpciesName) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (animalSpeciesId != null && !animalSpeciesId.isEmpty()) {
                criteria.add(Criteria.where("animalSpeciesId").regex(animalSpeciesId, "i")); // case-insensitive search
            }
            if (animalSpciesName != null) {
                criteria.add(Criteria.where("animalSpciesName").is(animalSpciesName));
            }

            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            }

            return mongoTemplate.find(query, Ticket.class);
        } catch(Exception e){
            throw new ServiceException("Error Searching Animal Species", e);
        }
    }
}