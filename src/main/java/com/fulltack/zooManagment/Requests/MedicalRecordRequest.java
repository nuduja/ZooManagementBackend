package com.fulltack.zooManagment.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MedicalRecordRequest {
    @NotNull
    private String animalId;
    private LocalDate recordDate;
    private String description;
}