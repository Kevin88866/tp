package seedu.cuddlecare;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.impl.ByeCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests for the CuddleCare main application class.
 *
 * These tests focus on verifying non-I/O behavior such as:
 *  - Proper initialization of command mappings.
 *  - HelpCommand dependency injection.
 *  - Greeting message correctness.
 *
 * Methods like run() and startApplicationLoop() are excluded as they involve
 * continuous user input and I/O side effects, which are better covered by integration testing.
 */

class CuddleCareTest {

    private CuddleCare app;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        app = new CuddleCare();
        System.setOut(new PrintStream(outContent)); // capture output
    }

    //@@author HarshitSrivastavaHS
    @Test
    void greet_nothing_greetsUser() {
        app.greet();
        String output = outContent.toString().trim();
        assertEquals("Hello! Welcome to CuddleCare.", output);
    }


    @Test
    void initialiseCommands_noInput_correctlyInitializesAllCommands() {
        app.initialiseCommands();
        Map<String, Command> commands = app.getCommandsForTesting();

        assertEquals(17, commands.size(), "Expected 17 registered commands");

        assertInstanceOf(ByeCommand.class, commands.get("bye"));
        assertInstanceOf(seedu.cuddlecare.command.impl.AddPetCommand.class, commands.get("add-pet"));
        assertInstanceOf(seedu.cuddlecare.command.impl.HelpCommand.class, commands.get("help"));
    }

    @Test
    void initialiseCommands_noInput_helpCommandReceivesCommandMap() {
        app.initialiseCommands();
        Map<String, Command> commands = app.getCommandsForTesting();

        Command helpCommand = commands.get("help");
        assertInstanceOf(seedu.cuddlecare.command.impl.HelpCommand.class, helpCommand);

        seedu.cuddlecare.command.impl.HelpCommand hc = (seedu.cuddlecare.command.impl.HelpCommand) helpCommand;
        assertTrue(hc.hasCommands(), "HelpCommand should have received command map");
    }
}
