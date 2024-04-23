package com.fulltack.zooManagment.service;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import com.fulltack.zooManagment.exception.AnimalNotFoundException;
import com.fulltack.zooManagment.generators.PDFGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fulltack.zooManagment.repository.AnimalRepository;
import com.fulltack.zooManagment.service.AnimalService;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.Requests.AnimalRequest;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private PDFGeneratorService pdfGeneratorService;

    @InjectMocks
    private AnimalService animalService;

    @Test
    void testConvertToAnimal() {
        LocalDate birthDate = LocalDate.of(1990,04,05);
        AnimalRequest animalRequest = new AnimalRequest("1", "Tiger", "Tigger", "001", birthDate, "India", "A Bengal tiger");
        Animal animal = animalService.convertToAnimal(animalRequest);
        assertThat(animal.getAnimalSpeciesId()).isEqualTo("1");
        assertThat(animal.getName()).isEqualTo("Tigger");
        assertThat(animal.getEnclosureId()).isEqualTo("001");
        assertThat(animal.getDescription()).isEqualTo("A Bengal tiger");
        assertNotNull(animal.getAnimalId());  // Ensure ID is generated
    }

    @Test
    void testGetAllAnimals() {
        when(animalRepository.findAll()).thenReturn(List.of(new Animal(), new Animal()));
        assertThat(animalService.getAllAnimals()).hasSize(2);
    }

    @Test
    void testGetAllAnimals_SuccessfulRetrieval() {
        when(animalRepository.findAll()).thenReturn(List.of(new Animal(), new Animal()));
        assertThat(animalService.getAllAnimals()).hasSize(2);
    }

    @Test
    void testAddAnimal_SuccessfulCreation() {
        LocalDate birthDate = LocalDate.of(2005, 8, 15);
        AnimalRequest animalRequest = new AnimalRequest("2", "Elephant", "Ella", "002", birthDate, "Thailand", "An Asian elephant");
        when(animalRepository.save(any(Animal.class))).thenReturn(new Animal());
        String response = animalService.addAnimal(animalRequest);
        assertThat(response).isEqualTo("Animal Created Successful");
        verify(animalRepository).save(any(Animal.class));  // Ensure save is called
    }




    @Test
    void testAddAnimal() {
        LocalDate birthDate = LocalDate.of(2005, 8, 15);
        AnimalRequest animalRequest = new AnimalRequest("2", "Elephant", "Ella", "002", birthDate, "Thailand", "An Asian elephant");
        when(animalRepository.save(any(Animal.class))).thenReturn(new Animal());
        String response = animalService.addAnimal(animalRequest);
        assertThat(response).isEqualTo("Animal Created Successful");
        verify(animalRepository).save(any(Animal.class));  // Ensure save is called
    }

//    @Test
//    void testGetAnimalByAnimalId_NotFound() {
//        when(animalRepository.findByAnimalId("123")).thenReturn(null);
//        assertThrows(AnimalNotFoundException.class, () -> animalService.getAnimalByAnimalId("123"));
//    }

    @Test
    void testGetAnimalByAnimalId_Found() {
        Animal animal = new Animal();
        animal.setAnimalId("123");
        when(animalRepository.findByAnimalId("123")).thenReturn(animal);
        assertThat(animalService.getAnimalByAnimalId("123")).isEqualTo(animal);
    }


//    @Test
//    void testGetAnimalByAnimalId_NotFound() {
//        when(animalRepository.findByAnimalId("123")).thenReturn(null);
//        assertThrows(AnimalNotFoundException.class, () -> animalService.getAnimalByAnimalId("123"));
//    }
//
//    @Test
//    void testGetAnimalByAnimalId_Found() {
//        Animal animal = new Animal();
//        animal.setAnimalId("123");
//        when(animalRepository.findByAnimalId("123")).thenReturn(animal);
//        assertThat(animalService.getAnimalByAnimalId("123")).isEqualTo(animal);
//    }

    @Test
    void testDeleteAnimalByAnimalId_ExistingAnimal() {
        when(animalRepository.existsByAnimalId("123")).thenReturn(true);
        String response = animalService.deleteAnimalByAnimalId("123");
        assertThat(response).isEqualTo("Animal Deleted Successfully");
        verify(animalRepository).deleteByAnimalId("123");
    }

    @Test
    void testDeleteAnimalByAnimalId_NonExistingAnimal() {
        when(animalRepository.existsByAnimalId("123")).thenReturn(false);
        String response = animalService.deleteAnimalByAnimalId("123");
        assertThat(response).isEqualTo("Animal Does not exists");
    }




}
