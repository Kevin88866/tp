package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListPetTreatmentsCommandTest {

    private PetList petList;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        Pet Mimi = new Pet("Mimi", "Cat", 1);
        Pet Dawg = new Pet("Dawg", "Dog", 2);

        petList = new PetList();
        petList.add(Mimi);
        petList.add(Dawg);

        Treatment T1 = new Treatment("Vaccination", LocalDate.parse("2025-10-11"));
        Treatment T2 = new Treatment("Dental Appointment", LocalDate.parse("2025-11-10"));
        Treatment T3 = new Treatment("Health Checkup", LocalDate.parse("2025-12-11"));

        Mimi.addTreatment(T1);
        Mimi.addTreatment(T2);
        Mimi.addTreatment(T3);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_validInput_listTreatments() {
        ListPetTreatmentsCommand command = new ListPetTreatmentsCommand(petList);

        String input = "n/Mimi";
        command.exec(input);

        assertTrue(outContent.toString().contains("Mimi's treatment history:\r\n" +
                "1.Vaccination on 2025-10-11\r\n" +
                "2.Dental Appointment on 2025-11-10\r\n" +
                "3.Health Checkup on 2025-12-11"));
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
