package seedu.cuddlecare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.command.impl.AddTreatmentCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link AddTreatmentCommand}.
 *
 * These tests validate that treatment records are added correctly for pets.
 */
class AddTreatmentCommandTest {

    private PetList petList;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        petList = new PetList();
        petList.add(new Pet("Fluffy", "Cat", 3));
        petList.add(new Pet("Buddy", "Dog", 5));

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_validInput_addsTreatment() {
        AddTreatmentCommand command = new AddTreatmentCommand(petList);

        String input = "n/Fluffy t/Vaccination d/2025-10-06";
        command.exec(input);

        Pet pet = petList.getPetByName("Fluffy");
        assertEquals(1, pet.getTreatments().size());
        assertEquals("Vaccination", pet.getTreatments().get(0).getName());
        assertEquals(LocalDate.of(2025, 10, 6), pet.getTreatments().get(0).getDate());

        assertTrue(outContent.toString().contains("Treatment added to Fluffy: Vaccination on 2025-10-06"));
    }

    @Test
    void exec_inputWithExtraSpaces_stillParses() {
        AddTreatmentCommand command = new AddTreatmentCommand(petList);

        String input = "  n/Fluffy   t/Annual Checkup   d/2025-11-01  ";
        command.exec(input);

        Pet pet = petList.getPetByName("Fluffy");
        assertEquals(1, pet.getTreatments().size());
        assertEquals("Annual Checkup", pet.getTreatments().get(0).getName());
        assertEquals(LocalDate.of(2025, 11, 1), pet.getTreatments().get(0).getDate());
    }

    @Test
    void exec_nonExistentPet_showsError() {
        AddTreatmentCommand command = new AddTreatmentCommand(petList);

        command.exec("n/Unknown t/Checkup d/2025-12-01");
        assertTrue(outContent.toString().contains("Pet not found: Unknown"));
    }

    @Test
    void exec_invalidDate_showsError() {
        AddTreatmentCommand command = new AddTreatmentCommand(petList);

        command.exec("n/Fluffy t/Checkup d/2025-13-01");
        assertTrue(outContent.toString().contains("Invalid date format"));
    }

    @Test
    void exec_missingArgument_showsError() {
        AddTreatmentCommand command = new AddTreatmentCommand(petList);

        command.exec("n/Fluffy t/Checkup");
        assertTrue(outContent.toString().contains("Invalid input. Usage: add-treatment"));
    }
}
