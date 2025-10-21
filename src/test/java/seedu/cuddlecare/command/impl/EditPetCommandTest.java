package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

/**
 * Tests for {@link EditPetCommand}.
 */
public class EditPetCommandTest {

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
        pets.add(new Pet("Milo", "Cat", 3));
        pets.add(new Pet("Luna", "Dog", 2));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void exec_editAllFields_success() {
        Command cmd = new EditPetCommand(pets);
        cmd.exec("n/Milo nn/Millie s/Dog a/4");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Updated Milo:"), "Missing update header.\n" + s);
        Assertions.assertTrue(s.contains("name → Millie"), "Expected name change.\n" + s);
        Assertions.assertTrue(s.contains("species → Dog"), "Expected species change.\n" + s);
        Assertions.assertTrue(s.contains("age → 4"), "Expected age change.\n" + s);
    }

    @Test
    void exec_renameConflict_printsConflictMessage() {
        Command cmd = new EditPetCommand(pets);
        cmd.exec("n/Milo nn/Luna");

        String s = out.toString();
        Assertions.assertTrue(s.contains("already exists"), "Expected name conflict message.\n" + s);
    }

    @Test
    void exec_unknownPet_printsNoSuchPet() {
        Command cmd = new EditPetCommand(pets);
        cmd.exec("n/Garfield a/10");

        String s = out.toString();
        Assertions.assertTrue(s.contains("No such pet: Garfield"), "Expected no such pet message.\n" + s);
    }

    @Test
    void exec_missingTargets_printsUsage() {
        Command cmd = new EditPetCommand(pets);
        cmd.exec("n/Milo");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Usage: edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]"),
                "Expected usage for missing fields.\n" + s);
    }

    @Test
    void exec_nonIntegerAge_printsNumberError() {
        Command cmd = new EditPetCommand(pets);
        cmd.exec("n/Milo a/x");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Age must be a valid number"),
                "Expected 'Age must be a valid number' message.\n" + s);
    }

    @Test
    void exec_withNullPetList_triggersAssertionWhenEnabled() {
        Assumptions.assumeTrue(assertionsEnabled(), "Assertions not enabled (-ea); skipping.");
        Command cmd = new EditPetCommand(null);
        Assertions.assertThrows(AssertionError.class, () -> cmd.exec("n/Milo a/4"),
                "With -ea, exec(null) should trigger an AssertionError");
    }

    boolean assertionsEnabled() {
        boolean enabled = false;
        assert enabled = true;
        return enabled;
    }
}
