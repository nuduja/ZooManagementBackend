package com.fulltack.zooManagment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "medicalrecords")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Id
    private String id;
    @Indexed(unique = true)
    private String medicalRecordId;
    private String animalId;
    private LocalDate recordDate;
    private String description;
}