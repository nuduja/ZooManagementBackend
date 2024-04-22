package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.EmployeeRequest;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Employee;
import com.fulltack.zooManagment.service.EmployeeService;
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
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = service.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
        try {
            Employee employee = service.getEmployeeById(employeeId);
            return ResponseEntity.ok(employee);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createEmployee")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            return ResponseEntity.ok(service.addEmployee(employeeRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        try {
            String result = service.deleteEmployeeById(id);
            return ResponseEntity.ok(result);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/searchEmployee")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nic,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String gender) {
        try {
            List<Employee> employees = service.searchEmployees(employeeId, name, nic, position, gender);
            return ResponseEntity.ok(employees);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updatebyemployeeid/{employeeId}")
    public String updateEmployeeByEmployeeId(@PathVariable String employeeId, @RequestBody Map<String, Object> updates) {
        service.updateEmployeeByEmployeeId(employeeId, updates);
        return "Employee updated successfully";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> employeeReport() {
        ByteArrayInputStream bis = service.generateEmployeesPDF();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employees.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}