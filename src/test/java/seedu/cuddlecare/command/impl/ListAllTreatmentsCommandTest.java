package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link ListAllTreatmentsCommand}
 */
class ListAllTreatmentsCommandTest {

    private ByteArrayOutputStream outContent;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

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

        Treatment t1 = new Treatment("Vaccination", null, LocalDate.parse("2025-11-11"));
        Treatment t2 = new Treatment("Dental Appointment", null, LocalDate.parse("2025-12-20"));
        Treatment t3 = new Treatment("Health Checkup", null, LocalDate.parse("2025-10-09"));

        cat.addTreatment(t1);
        dog.addTreatment(t2);
        hamster.addTreatment(t3);

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
