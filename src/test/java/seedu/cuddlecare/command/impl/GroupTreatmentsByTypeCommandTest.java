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
 * Tests for {@link GroupTreatmentsByTypeCommand}.
 */
public class GroupTreatmentsByTypeCommandTest {

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
    void exec_groupAcrossAllPets_printsGroupedByType() {
        Command cmd = new GroupTreatmentsByTypeCommand(pets);
        cmd.exec("");

        String s = out.toString();

        Assertions.assertTrue(s.contains("Treatments grouped by type:"), "Missing header.\n" + s);

        int iCheckup = s.indexOf("== Checkup ==");
        int iDeworm  = s.indexOf("== Deworm ==");
        int iVaccine = s.indexOf("== Vaccine ==");

        Assertions.assertTrue(iCheckup >= 0, "Missing Checkup group.\n" + s);
        Assertions.assertTrue(iDeworm  >= 0, "Missing Deworm group.\n" + s);
        Assertions.assertTrue(iVaccine >= 0, "Missing Vaccine group.\n" + s);
        Assertions.assertTrue(iCheckup < iDeworm && iDeworm < iVaccine,
                "Groups not in alphabetical order.\n" + s);

        int mVax = s.indexOf("Milo: [ ] Vaccine A on 2024-01-10", iVaccine);
        int lVax = s.indexOf("Luna: [ ] Vaccine B on 2024-01-15", iVaccine);
        Assertions.assertTrue(mVax > 0 && lVax > mVax,
                "Vaccine items not sorted by date ascending or missing.\n" + s);

        Assertions.assertTrue(s.contains("Milo: [ ] Checkup on 2024-02-05"),
                "Missing Checkup item.\n" + s);
        Assertions.assertTrue(s.contains("Luna: [ ] Deworm on 2024-03-01"),
                "Missing Deworm item.\n" + s);
    }

    @Test
    void exec_groupSinglePet_onlyThatPetsItems() {
        Command cmd = new GroupTreatmentsByTypeCommand(pets);
        cmd.exec("n/Milo");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Milo's treatments grouped by type:"),
                "Missing per-pet header.\n" + s);

        Assertions.assertTrue(s.contains("Milo: [ ] Vaccine A on 2024-01-10"), "Missing Milo vaccine.\n" + s);
        Assertions.assertTrue(s.contains("Milo: [ ] Checkup on 2024-02-05"), "Missing Milo checkup.\n" + s);

        Assertions.assertFalse(s.contains("Luna:"),
                "Per-pet grouping should not include other pets.\n" + s);
    }

    @Test
    void exec_unknownPet_printsNoSuchPet() {
        Command cmd = new GroupTreatmentsByTypeCommand(pets);
        cmd.exec("n/Garfield");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No such pet: Garfield"),
                "Expected 'No such pet' message.\n" + s);
    }

    @Test
    void exec_petWithNoTreatments_printsEmptyMsg() {
        Pet zero = new Pet("Zero", "Cat", 1);
        pets.add(zero);

        Command cmd = new GroupTreatmentsByTypeCommand(pets);
        cmd.exec("n/Zero");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No treatments for Zero to group."),
                "Expected 'no treatments for pet' message.\n" + s);
    }

    @Test
    void exec_withNullPetList_triggersAssertionWhenEnabled() {
        Assumptions.assumeTrue(assertionsEnabled(), "Assertions not enabled (-ea); skipping.");
        Command cmd = new GroupTreatmentsByTypeCommand(null);
        Assertions.assertThrows(AssertionError.class, () -> cmd.exec(""),
                "With -ea, null PetList should assert.");
    }

    boolean assertionsEnabled() {
        boolean enabled = false;
        assert enabled = true;
        return enabled;
    }
}
