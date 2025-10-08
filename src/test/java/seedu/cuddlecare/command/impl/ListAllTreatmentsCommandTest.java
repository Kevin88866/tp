package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link ListAllTreatmentsCommand}
 */
class ListAllTreatmentsCommandTest {

    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_nonEmptyList_printsList() {
        PetList pets = new PetList();
        Pet cat = new Pet("Mimi", "Cat", 1);
        Pet dog = new Pet("Snoopy", "Dog", 2);
        Pet hamster = new Pet("Hamham", "Hamster", 1);

        pets.add(cat);
        pets.add(dog);
        pets.add(hamster);

        Treatment T1 = new Treatment("Vaccination", LocalDate.parse("2025-11-11"));
        Treatment T2 = new Treatment("Dental Appointment", LocalDate.parse("2025-12-20"));
        Treatment T3 = new Treatment("Health Checkup", LocalDate.parse("2025-10-09"));

        cat.addTreatment(T1);
        dog.addTreatment(T2);
        hamster.addTreatment(T3);

        ListAllTreatmentsCommand command = new ListAllTreatmentsCommand(pets);
        command.exec("");

        String[] lines = outContent.toString().trim().split("\\R");

        assertEquals("Hamham: [ ] Health Checkup on 2025-10-09", lines[0]);
        assertEquals("Mimi: [ ] Vaccination on 2025-11-11", lines[1]);
        assertEquals("Snoopy: [ ] Dental Appointment on 2025-12-20", lines[2]);
    }

    @Test
    void exec_emptyPetList_printsMessage() {
        PetList pets = new PetList();

        ListAllTreatmentsCommand command = new ListAllTreatmentsCommand(pets);
        command.exec("");

        assertEquals("No treatments logged.", outContent.toString().trim());
    }

    @Test
    void exec_emptyTreatmentList_printsMessage() {
        PetList pets = new PetList();
        Pet cat = new Pet("Mimi", "Cat", 1);
        Pet dog = new Pet("Snoopy", "Dog", 2);
        Pet hamster = new Pet("Hamham", "Hamster", 1);

        pets.add(cat);
        pets.add(dog);
        pets.add(hamster);

        ListAllTreatmentsCommand command = new ListAllTreatmentsCommand(pets);
        command.exec("");

        assertEquals("No treatments logged.", outContent.toString().trim());
    }
}
