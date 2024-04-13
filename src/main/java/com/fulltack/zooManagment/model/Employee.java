package com.fulltack.zooManagment.model;

import com.fulltack.zooManagment.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private String id;
    private String employeeId;
    private String name;
    private String nic;
    private String address;
    private String phone;
    private String position;
    private Gender gender;
    private LocalDate dob;
}