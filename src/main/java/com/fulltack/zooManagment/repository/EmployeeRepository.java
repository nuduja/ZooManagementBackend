package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.AnimalSpecies;
import com.fulltack.zooManagment.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
//    AnimalSpecies findByAnimalSpeciesName(String name);

    Employee findByEmployeeId(String employeeId);

//    boolean existsByAnimalSpeciesName(String name);

//    String deleteByAnimalSpeciesName(String name);
}