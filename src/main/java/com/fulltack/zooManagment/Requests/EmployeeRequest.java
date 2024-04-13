package com.fulltack.zooManagment.Requests;

import com.fulltack.zooManagment.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeRequest {
    @NotNull
    private String name;
    @NotNull
    private String nic;
    private String address;
    private String phone;
    private String position;
    private Gender gender;
    private LocalDate dob;
}