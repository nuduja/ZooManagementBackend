package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.model.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, String> {
//    Animal findByAnimalId(String animalId);
    MedicalRecord findByMedicalRecordId(String medicalRecordId);
    List <MedicalRecord> findByAnimalId(String medicalRecordId);
//
//    List<Animal> findByAnimalSpeciesId(String animalSpeciesId);
//
    boolean existsByMedicalRecordId(String medicalRecordId);
//
    String deleteByMedicalRecordId(String medicalRecordId);

}