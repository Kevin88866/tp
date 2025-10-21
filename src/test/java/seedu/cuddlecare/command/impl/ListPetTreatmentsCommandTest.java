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

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListPetTreatmentsCommandTest {

    private PetList petList;
    private ByteArrayOutputStream outContent;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        Pet mimi = new Pet("Mimi", "Cat", 1);
        Pet dawg = new Pet("Dawg", "Dog", 2);

        petList = new PetList();
        petList.add(mimi);
        petList.add(dawg);

        Treatment t1 = new Treatment("Vaccination", null, LocalDate.parse("2025-10-11"));
        Treatment t2 = new Treatment("Dental Appointment", null, LocalDate.parse("2025-11-10"));
        Treatment t3 = new Treatment("Health Checkup", null, LocalDate.parse("2025-12-11"));

        mimi.addTreatment(t1);
        mimi.addTreatment(t2);
        mimi.addTreatment(t3);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_validInput_listTreatments() {
        ListPetTreatmentsCommand command = new ListPetTreatmentsCommand(petList);

        String input = "n/Mimi";
        command.exec(input);

        assertTrue(outContent.toString().contains("Mimi's treatment history:"));
        assertTrue(outContent.toString().contains("1.[ ] Vaccination on 2025-10-11"));
        assertTrue(outContent.toString().contains("2.[ ] Dental Appointment on 2025-11-10"));
        assertTrue(outContent.toString().contains("3.[ ] Health Checkup on 2025-12-11"));
    }

    @Test
    void exec_invalidInput_showsError() {
        ListPetTreatmentsCommand command = new ListPetTreatmentsCommand(petList);

        String input = "Mimi";
        command.exec(input);

        assertTrue(outContent.toString().contains("Invalid input. Usage: list-treatments n/PET_NAME"));
    }

    @Test
    void exec_nonExistentPet_showsError() {
        ListPetTreatmentsCommand command = new ListPetTreatmentsCommand(petList);

        command.exec("n/Snoopy");
        assertTrue(outContent.toString().contains("Pet not found: Snoopy"));
    }

    @Test
    void exec_emptyList_showsError() {
        ListPetTreatmentsCommand command = new ListPetTreatmentsCommand(petList);

        command.exec("n/Dawg");
        assertTrue(outContent.toString().contains("Dawg has no logged treatments."));
    }
}
