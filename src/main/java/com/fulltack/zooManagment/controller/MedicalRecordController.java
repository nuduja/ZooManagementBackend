package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.AnimalRequest;
import com.fulltack.zooManagment.Requests.MedicalRecordRequest;
import com.fulltack.zooManagment.exception.AnimalNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.model.MedicalRecord;
import com.fulltack.zooManagment.service.MedicalRecordService;
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
@RequestMapping("/api/v1/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService service;

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        return ResponseEntity.ok(service.getAllMedicalRecords());
    }

                    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByMedicalRecordId(@PathVariable String medicalRecordId) {
        try {
            return ResponseEntity.ok(service.getMedicalRecordByMedicalRecordId(medicalRecordId));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byAnimalId/{animalId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByAnimalId(@PathVariable String animalId) {
        try {
            List<MedicalRecord> medicalRecord = service.getMedicalRecordsByAnimalId(animalId);

            if (medicalRecord.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(medicalRecord);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AnimalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createMedicalRecord")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest) {
        try {
            return ResponseEntity.ok(service.addMedicalRecord(medicalRecordRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{medicalRecordId}")
    public ResponseEntity<String> deleteMedicalRecordByMedicalRecordId(@PathVariable String medicalRecordId) {
        try {
            return ResponseEntity.ok(service.deleteMedicalRecordByMedicalRecordId(medicalRecordId));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/searchmedicalrecords")
    public List<MedicalRecord> searchMedicalRecords(
            @RequestParam(required = false) String medicalRecordId,
            @RequestParam(required = false) String animalId){
        return service.searchMedicalRecords(medicalRecordId, animalId);
    }

    @PutMapping("/updatebymedicalrecordid/{medicalRecordId}")
    public String updateMedicalRecordByMedicalRecordId(@PathVariable String medicalRecordId, @RequestBody Map<String, Object> updates) {
        service.updateMedicalRecordByMedicalRecordId(medicalRecordId, updates);
        return "Medical Record Details updated successfully";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> ticketsReport() {
        ByteArrayInputStream bis = service.generateAnimalsPDF();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=animals.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}