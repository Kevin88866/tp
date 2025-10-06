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
 * <p>These tests validate that treatment records are added correctly for pets,
 * and that invalid input cases are handled gracefully.
 */
class AddTreatmentCommandTest {

    private PetList pets;
    private AddTreatmentCommand command;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        pets = new PetList();
        pets.add(new Pet("Peanut", "Dog", 2));
        pets.add(new Pet("Milo", "Cat", 3));
        command = new AddTreatmentCommand(pets);
        System.setOut(new PrintStream(outContent)); // capture console output
    }

    @Test
    void execute_validInput_addsTreatmentSuccessfully() {
        command.exec("p/1 n/Rabies d/2025-10-06 t/Vaccination");
        String output = outContent.toString().trim();

        assertTrue(output.contains("Treatment \"Rabies\" added for Peanut."));

        Pet pet = pets.getPetByIndex(0);
        assertEquals(1, pet.getTreatments().size());
        Treatment treatment = pet.getTreatments().get(0);
        assertEquals("Rabies", treatment.getName());
        assertEquals(LocalDate.of(2025, 10, 6), treatment.getDate());
        assertEquals("Vaccination", treatment.getType());
    }

    @Test
    void execute_invalidPetIndex_showsErrorMessage() {
        command.exec("p/10 n/Rabies d/2025-10-06 t/Vaccination");
        String output = outContent.toString().trim();
        assertTrue(output.contains("No pet found with that index."));
    }

    @Test
    void execute_invalidDateFormat_showsErrorMessage() {
        command.exec("p/1 n/Rabies d/10-06-2025 t/Vaccination");
        String output = outContent.toString().trim();
        assertTrue(output.contains("Error: Invalid date format"));
    }

    @Test
    void execute_missingFields_showsUsageMessage() {
        command.exec("p/1 n/Rabies");
        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid input. Usage"));
    }
}
