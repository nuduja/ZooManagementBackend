package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.EmployeeRequest;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.model.Employee;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Employee convertToEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setEmployeeId(UUID.randomUUID().toString().split("-")[0]);
        employee.setName(employeeRequest.getName());
        employee.setNic(employeeRequest.getNic());
        employee.setAddress(employeeRequest.getAddress());
        employee.setPhone(employeeRequest.getPhone());
        employee.setPosition(employeeRequest.getPosition());
        employee.setGender(employeeRequest.getGender());
        employee.setDob(employeeRequest.getDob());
        return employee;
    }

    public List<Employee> getAllEmployees() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching all employees", e);
        }
    }

    public Employee getEmployeeById(String employeeId) {
        try {
            Employee employee = repository.findByEmployeeId(employeeId);
            if (employee == null) {
                throw new TicketNotFoundException("Employee with ID " + employeeId + " not found");
            }
            return employee;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific employee", e);
        }
    }

    public String addEmployee(EmployeeRequest employeeRequest) {
        try {
            Employee employee = convertToEmployee(employeeRequest);
            repository.save(employee);
            return "Employee Created Successfully";
        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding an employee", e);
        }
    }

//    public String updateEmployee(String id, EmployeeRequest employeeRequest) {
//        try {
//            Employee employee = repository.findById(id)
//            employee.setName(employeeRequest.getName());
//            employee.setNic(employeeRequest.getNic());
//            employee.setAddress(employeeRequest.getAddress());
//            employee.setPhone(employeeRequest.getPhone());
//            employee.setPosition(employeeRequest.getPosition());
//            employee.setDob(employeeRequest.getDob());
//            repository.save(employee);
//            return "Employee Updated Successfully";
//        } catch (Exception e) {
//            throw new ServiceException("Error occurred while updating an employee", e);
//        }
//    }

    public String deleteEmployeeById(String id) {
        try {
            repository.deleteById(id);
            return "Employee Deleted Successfully";
        } catch (Exception e) {
            throw new ServiceException("Error occurred while deleting an employee", e);
        }
    }

    public List<Employee> searchEmployees(String employeeId, String name, String nic, String position, String gender) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (employeeId != null && !employeeId.isEmpty()) {
                criteria.add(Criteria.where("employeeId").is(employeeId));
            }
            if (name != null && !name.isEmpty()) {
                criteria.add(Criteria.where("name").is(name));
            }
            if (nic != null && !nic.isEmpty()) {
                criteria.add(Criteria.where("nic").is(nic));
            }
            if (position != null && !position.isEmpty()) {
                criteria.add(Criteria.where("position").is(position));
            }
            if (gender != null && !gender.isEmpty()) {
                criteria.add(Criteria.where("gender").is(gender));
            }

            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            }

            return mongoTemplate.find(query, Employee.class);
        } catch (Exception e) {
            throw new ServiceException("Error Searching Employee", e);
        }
    }

    public void updateEmployeeByEmployeeId(String employeeId, Map<String, Object> updates) {
        Query query = new Query(Criteria.where("employeeId").is(employeeId));
        Update update = new Update();
        updates.forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("employeeId")) {
                update.set(key, value);
            }
        });
        mongoTemplate.findAndModify(query, update, Employee.class);
    }
}