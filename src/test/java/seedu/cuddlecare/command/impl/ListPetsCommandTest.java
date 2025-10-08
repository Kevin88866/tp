package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for {@link ListPetsCommand}.
 */
public class ListPetsCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void exec_emptyList_printsNoPetsFound() {
        PetList pets = new PetList();
        Command cmd = new ListPetsCommand(pets);

        cmd.exec(""); // args ignored

        String s = out.toString();
        Assertions.assertTrue(s.contains("No pets found."),
                "Should print 'No pets found.' when list is empty.\nActual:\n" + s);
    }

    @Test
    void exec_nonEmptyList_printsNumberedPets() {
        PetList pets = new PetList();
        Pet milo = new Pet("Milo", "Dog", 2);
        Pet luna = new Pet("Luna", "Cat", 3);
        pets.add(milo);
        pets.add(luna);

        Command cmd = new ListPetsCommand(pets);
        cmd.exec("");

        String s = out.toString();
        Assertions.assertTrue(s.contains("Here are your pets:"), "Missing header.\n" + s);
        Assertions.assertTrue(s.contains("1. " + milo.toString()), "Missing/incorrect 1st line.\n" + s);
        Assertions.assertTrue(s.contains("2. " + luna.toString()), "Missing/incorrect 2nd line.\n" + s);
    }
}
