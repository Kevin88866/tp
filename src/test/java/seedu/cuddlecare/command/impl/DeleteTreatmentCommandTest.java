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
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link DeleteTreatmentCommand}
 */
class DeleteTreatmentCommandTest {

    private PetList petList;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        // Create pets and add to list
        Pet peanut = new Pet("Peanut", "Dog", 2);
        petList = new PetList();
        petList.add(peanut);

        // Add treatments
        Treatment t1 = new Treatment("Rabies Vaccine", LocalDate.parse("2025-10-10"));
        Treatment t2 = new Treatment("Grooming", LocalDate.parse("2025-10-20"));

        peanut.addTreatment(t1);
        peanut.addTreatment(t2);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exec_validDelete_removesTreatmentSuccessfully() {
        DeleteTreatmentCommand command = new DeleteTreatmentCommand(petList);
        command.exec("n/Peanut i/1"); // This deletes first treatment (Rabies Vaccine)

        String output = outContent.toString().trim();
        assertTrue(output.contains("Deleted treatment \"Rabies Vaccine\" for Peanut."),
                "Expected to delete 'Rabies Vaccine' but got: " + output);
        assertEquals(1, petList.getPetByName("Peanut").getTreatments().size());
    }

    @Test
    void exec_deleteSecondTreatment_removesGrooming() {
        DeleteTreatmentCommand command = new DeleteTreatmentCommand(petList);
        command.exec("n/Peanut i/2"); // This deletes second treatment (Grooming)

        String output = outContent.toString().trim();
        assertTrue(output.contains("Deleted treatment \"Grooming\" for Peanut."),
                "Expected to delete 'Grooming' but got: " + output);
        assertEquals(1, petList.getPetByName("Peanut").getTreatments().size());
    }

    @Test
    void exec_invalidPetName_printsNotFoundMessage() {
        DeleteTreatmentCommand command = new DeleteTreatmentCommand(petList);
        command.exec("n/Snowy i/1");

        String output = outContent.toString().trim();
        assertEquals("Pet not found: Snowy", output);
    }

    @Test
    void exec_invalidIndex_printsErrorMessage() {
        DeleteTreatmentCommand command = new DeleteTreatmentCommand(petList);
        command.exec("n/Peanut i/5");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid treatment index"));
    }

    @Test
    void exec_missingArguments_printsUsageMessage() {
        DeleteTreatmentCommand command = new DeleteTreatmentCommand(petList);
        command.exec("n/Peanut");

        String output = outContent.toString().trim();
        assertTrue(output.contains("Invalid input. Usage: delete-treatment n/PET_NAME i/INDEX"));
    }
}

