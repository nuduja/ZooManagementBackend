package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.AnimalRequest;
import com.fulltack.zooManagment.Requests.MedicalRecordRequest;
import com.fulltack.zooManagment.exception.AnimalNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.generators.PDFGeneratorService;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.model.MedicalRecord;
import com.fulltack.zooManagment.repository.AnimalRepository;
import com.fulltack.zooManagment.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository repository;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @Autowired
    private MongoTemplate mongoTemplate;

    public MedicalRecord convertToMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedicalRecordId(UUID.randomUUID().toString().split("-")[0]);
        medicalRecord.setAnimalId(medicalRecordRequest.getAnimalId());
        medicalRecord.setRecordDate(medicalRecordRequest.getRecordDate());
        medicalRecord.setDescription(medicalRecordRequest.getDescription());
        return medicalRecord;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Medical Record", e);
        }
    }

    public MedicalRecord getMedicalRecordByMedicalRecordId(String medicalRecordId) {
        try {
            MedicalRecord medicalRecord = repository.findByMedicalRecordId(medicalRecordId);

            if (medicalRecord == null) {
                throw new AnimalNotFoundException("Medical Record with ID " + medicalRecordId + " not found");
            }

            return medicalRecord;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific Medical Record", e);
        }
    }

    public List<MedicalRecord> getMedicalRecordsByAnimalId(String animalId) {
        try {
            return repository.findByAnimalId(animalId);
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Medical Records", e);
        }
    }

    public String addMedicalRecord(MedicalRecordRequest medicalRecordRequest) {
        try {
            MedicalRecord medicalRecord = convertToMedicalRecord(medicalRecordRequest);
            if (medicalRecord.getMedicalRecordId() == null || medicalRecord.getAnimalId() == null) {
                throw new IllegalArgumentException("Medical Record ID and Animal ID must be valid.");
            }
            repository.save(medicalRecord);
            return "Medical Record Created Successful";
        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding an Medical Record", e);
        }
    }

    public String deleteMedicalRecordByMedicalRecordId(String medicalRecordId) {
        try {
            if (repository.existsByMedicalRecordId(medicalRecordId)) {
                repository.deleteByMedicalRecordId(medicalRecordId);
                return "Medical Record Deleted Successfully";
            } else {
                return "Medical Record Does not exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting Medical Record", e);
        }
    }

    public List<MedicalRecord> searchMedicalRecords(String medicalRecordId, String animalId) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (medicalRecordId != null && !medicalRecordId.isEmpty()) {
                criteria.add(Criteria.where("medicalRecordId").regex(medicalRecordId, "i"));
            }
            if (animalId != null && !animalId.isEmpty()) {
                criteria.add(Criteria.where("animalId").regex(animalId, "i"));
            }

            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            }

            return mongoTemplate.find(query, MedicalRecord.class);
        } catch (Exception e) {
            throw new ServiceException("Error Searching Medical Record", e);
        }
    }

    public void updateMedicalRecordByMedicalRecordId(String medicalRecordId, Map<String, Object> updates) {
        Query query = new Query(Criteria.where("medicalRecordId").is(medicalRecordId));
        Update update = new Update();
        updates.forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("medicalRecordId")) {
                update.set(key, value);
            }
        });
        mongoTemplate.findAndModify(query, update, MedicalRecord.class);
    }

    public ByteArrayInputStream generateAnimalsPDF() {
        List<MedicalRecord> medicalRecord = repository.findAll();
        return pdfGeneratorService.MedicalRecordReport(medicalRecord);
    }
}