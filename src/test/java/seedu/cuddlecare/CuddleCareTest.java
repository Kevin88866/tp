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

class CuddleCareTest {

    private CuddleCare app;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

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

    @Test
    void greet_nothing_greetsUser() {
        app.greet();
        String output = outContent.toString().trim();
        assertEquals("Hello! Welcome to CuddleCare.", output);
    }

    @Test
    void printInputPrompt_nothing_prompt() {
        app.printInputPrompt();
        String output = outContent.toString();
        assertEquals("> ", output);
    }

    @Test
    void testInitialiseCommands() {
        app.initialiseCommands();
        Map<String, Command> commands = app.getCommandsForTesting();
        assertTrue(commands.containsKey("bye"));
        assertInstanceOf(ByeCommand.class, commands.get("bye"));
    }
}
