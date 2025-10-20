package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

/**
 * Tests for {@link FilterTreatmentsByDateCommand}.
 */
public class FilterTreatmentsByDateCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    private PetList pets;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.SEVERE);
    }

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        pets = new PetList();

        Pet milo = new Pet("Milo", "Cat", 3);
        milo.addTreatment(new Treatment("Vaccine A", LocalDate.of(2024, 1, 10)));
        milo.addTreatment(new Treatment("Checkup", LocalDate.of(2024, 2, 5)));

        Pet luna = new Pet("Luna", "Dog", 2);
        luna.addTreatment(new Treatment("Vaccine B", LocalDate.of(2024, 1, 15)));
        luna.addTreatment(new Treatment("Deworm", LocalDate.of(2024, 3, 1)));

        pets.add(milo);
        pets.add(luna);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void exec_filterAllPetsWithinRange_printsMatches() {
        Command cmd = new FilterTreatmentsByDateCommand(pets);
        cmd.exec("from/2024-01-01 to/2024-01-31");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Treatments between 2024-01-01 and 2024-01-31:"),
                "Missing/incorrect header.\n" + s);
        Assertions.assertTrue(s.contains("Milo: [ ] Vaccine A on 2024-01-10"),
                "Missing Milo's Vaccine A.\n" + s);
        Assertions.assertTrue(s.contains("Luna: [ ] Vaccine B on 2024-01-15"),
                "Missing Luna's Vaccine B.\n" + s);
        Assertions.assertFalse(s.contains("2024-02-05"),
                "Should not include Feb treatment.\n" + s);
    }

    @Test
    void exec_filterSinglePetWithinRange_printsMatchesForPet() {
        Command cmd = new FilterTreatmentsByDateCommand(pets);
        cmd.exec("n/Milo from/2024-01-01 to/2024-12-31");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Milo's treatments between 2024-01-01 and 2024-12-31:"),
                "Missing/incorrect header for pet.\n" + s);
        Assertions.assertTrue(s.contains("Milo: [ ] Vaccine A on 2024-01-10"), "Missing Milo Jan.\n" + s);
        Assertions.assertTrue(s.contains("Milo: [ ] Checkup on 2024-02-05"), "Missing Milo Feb.\n" + s);
        Assertions.assertFalse(s.contains("Luna:"), "Should not include other pets.\n" + s);
    }

    @Test
    void exec_noMatches_printsEmptyMessage() {
        Command cmd = new FilterTreatmentsByDateCommand(pets);
        cmd.exec("from/2025-01-01 to/2025-01-31");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No treatments found for all pets between 2025-01-01 and 2025-01-31."),
                "Expected no matches message.\n" + s);
    }

    @Test
    void exec_invalidDate_printsError() {
        Command cmd = new FilterTreatmentsByDateCommand(pets);
        cmd.exec("from/2024-13-01 to/2024-01-31");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Invalid date. Use YYYY-MM-DD."),
                "Expected invalid date error.\n" + s);
    }

    @Test
    void exec_petNotFound_printsMessage() {
        Command cmd = new FilterTreatmentsByDateCommand(pets);
        cmd.exec("n/Garfield from/2024-01-01 to/2024-12-31");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No such pet: Garfield"), "Expected 'No such pet' message.\n" + s);
    }

    @Test
    void exec_withNullPetList_triggersAssertionWhenEnabled() {
        Assumptions.assumeTrue(assertionsEnabled(), "Assertions not enabled (-ea); skipping.");
        Command cmd = new FilterTreatmentsByDateCommand(null);
        Assertions.assertThrows(AssertionError.class, () -> cmd.exec("from/2024-01-01 to/2024-12-31"),
                "With -ea, exec should assert on null PetList");
    }

    boolean assertionsEnabled() {
        boolean enabled = false;
        assert enabled = true;
        return enabled;
    }
}
