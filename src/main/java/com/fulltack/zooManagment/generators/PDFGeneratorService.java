package com.fulltack.zooManagment.generators;

import com.fulltack.zooManagment.model.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFGeneratorService {

    public ByteArrayInputStream ticketReport(List<Ticket> tickets) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (Ticket ticket : tickets) {
                document.add(new Paragraph("Ticket ID: " + ticket.getTicketID()));
                document.add(new Paragraph("Ticket Type: " + ticket.getTicketType()));
                document.add(new Paragraph("Price: " + ticket.getPrice()));
                document.add(new Paragraph("Status: " + ticket.getStatus()));
                document.add(new Paragraph("Ticket Date: " + ticket.getTicketDate()));
                document.add(new Paragraph("Username: " + ticket.getUsername()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream adminReport(List<Admin> admins) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (Admin admin : admins) {
                document.add(new Paragraph("Admin ID: " + admin.getAdminId()));
                document.add(new Paragraph("Admin Name: " + admin.getName()));
                document.add(new Paragraph("Admin Username: " + admin.getUsername()));
                document.add(new Paragraph("Admin Role: " + admin.getRole()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream userReport(List<User> users) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (User user : users) {
                document.add(new Paragraph("User ID: " + user.getUserId()));
                document.add(new Paragraph("User Name: " + user.getName()));
                document.add(new Paragraph("User Username: " + user.getUsername()));
                document.add(new Paragraph("User Phone Number: " + user.getPhone()));
                document.add(new Paragraph("User Email: " + user.getEmail()));
                document.add(new Paragraph("User Role: " + user.getRole()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream animalReport(List<Animal> animals) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (Animal animal : animals) {
                document.add(new Paragraph("Animal ID: " + animal.getAnimalId()));
                document.add(new Paragraph("Animal Species ID: " + animal.getAnimalSpeciesId()));
                document.add(new Paragraph("Animal Species Name: " + animal.getAnimalSpeciesName()));
                document.add(new Paragraph("Animal Name: " + animal.getName()));
                document.add(new Paragraph("Animal Enclosure ID: " + animal.getEnclosureId()));
                document.add(new Paragraph("Animal Birth Date: " + animal.getBirthDate()));
                document.add(new Paragraph("Animal Birth Country: " + animal.getBirthCountry()));
                document.add(new Paragraph("Animal Description: " + animal.getDescription()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream animalSpeciesReport(List<AnimalSpecies> animalSpecieses) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (AnimalSpecies animalSpecies : animalSpecieses) {
                document.add(new Paragraph("Animal Species ID: " + animalSpecies.getAnimalSpeciesId()));
                document.add(new Paragraph("Animal Species Name: " + animalSpecies.getAnimalSpeciesName()));
                document.add(new Paragraph("Animal Species Taxonomy Kingdom: " + animalSpecies.getTaxonomy_kingdom()));
                document.add(new Paragraph("Animal Species Taxonomy Scientific Name: " + animalSpecies.getTaxonomy_scientific_name()));
                document.add(new Paragraph("Animal Species Characteristics Group Behavior: " + animalSpecies.getCharacteristics_group_behavior()));
                document.add(new Paragraph("Animal Species Characteristics Diet: " + animalSpecies.getCharacteristics_diet()));
                document.add(new Paragraph("Animal Species Characteristics Skin Type: " + animalSpecies.getCharacteristics_skin_type()));
                document.add(new Paragraph("Animal Species Characteristics Top Speed: " + animalSpecies.getCharacteristics_top_speed()));
                document.add(new Paragraph("Animal Species Characteristics Lifespan: " + animalSpecies.getCharacteristics_lifespan()));
                document.add(new Paragraph("Animal Species Characteristics Weight: " + animalSpecies.getCharacteristics_weight()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream employeeReport(List<Employee> employees) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (Employee employee : employees) {
                document.add(new Paragraph("Employee ID: " + employee.getEmployeeId()));
                document.add(new Paragraph("Employee Name: " + employee.getName()));
                document.add(new Paragraph("Employee NIC: " + employee.getNic()));
                document.add(new Paragraph("Employee Address: " + employee.getAddress()));
                document.add(new Paragraph("Employee Phone: " + employee.getPhone()));
                document.add(new Paragraph("Employee Position: " + employee.getPosition()));
                document.add(new Paragraph("Employee Gender: " + employee.getGender()));
                document.add(new Paragraph("Employee Date of Birth: " + employee.getDob()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }



    public ByteArrayInputStream MedicalRecordReport(List<MedicalRecord> medicalRecords) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (MedicalRecord medicalRecord : medicalRecords) {
                document.add(new Paragraph("Medical Record ID: " + medicalRecord.getMedicalRecordId()));
                document.add(new Paragraph("Animal ID: " + medicalRecord.getAnimalId()));
                document.add(new Paragraph("Record Date: " + medicalRecord.getRecordDate()));
                document.add(new Paragraph("Description: " + medicalRecord.getDescription()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream eventReport(List<Event> events) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            for (Event event : events) {
                document.add(new Paragraph("Event ID: " + event.getEventID()));
                document.add(new Paragraph("Event Name: " + event.getEventName()));
                document.add(new Paragraph("Event Description: " + event.getEventDescription()));
                document.add(new Paragraph("Event Date: " + event.getEventDate()));
                document.add(new Paragraph("Event Location: " + event.getEventLocation()));
                document.add(new Paragraph("Capacity: " + event.getCapacity()));
                document.add(new Paragraph("Event Manager: " + event.getEventManager()));
                document.add(new Paragraph("Username: " + event.getUsername()));
                document.add(new Paragraph(" "));
            }

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}