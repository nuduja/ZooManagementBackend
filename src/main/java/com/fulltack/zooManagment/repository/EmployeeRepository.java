package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Employee findByEmployeeId(String employeeId);
}