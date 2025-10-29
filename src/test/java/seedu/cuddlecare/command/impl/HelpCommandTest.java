package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link HelpCommand}.
 */
public class HelpCommandTest {

    private HelpCommand helpCommand;
    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUp() {
        helpCommand = new HelpCommand();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testExec_noArgumentsProvided_allCommandsPrintedInCategoryOrder() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("add-pet", new AddPetCommand(new PetList()));
        commands.put("mark", new MarkTreatmentCommand(new PetList()));
        commands.put("help", new HelpCommand());

        helpCommand.setCommands(commands);
        helpCommand.exec("");

        String output = outContent.toString();

        int generalIndex = output.indexOf("General");
        int petIndex = output.indexOf("Pet");
        int treatmentIndex = output.indexOf("Treatment");

        assertTrue(generalIndex < petIndex, "General should appear before Pet");
        assertTrue(petIndex < treatmentIndex, "Pet should appear before Treatment");

        assertTrue(output.contains(commands.get("help").getShortDescription()));
        assertTrue(output.contains(commands.get("add-pet").getShortDescription()));
        assertTrue(output.contains(commands.get("mark").getShortDescription()));

        assertTrue(output.contains("Run \"help [c/COMMAND_NAME]\" to find out more about a command."));
    }

    @Test
    public void testExec_commandNameProvided_specificCommandHelpPrinted() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("add-pet", new AddPetCommand(new PetList()));
        commands.put("mark", new MarkTreatmentCommand(new PetList()));
        commands.put("help", new HelpCommand());

        helpCommand.setCommands(commands);

        helpCommand.exec("c/add-pet");

        Command addPet = commands.get("add-pet");

        String output = outContent.toString();
        assertTrue(output.contains("Command Name: add-pet"));
        assertTrue(output.contains(String.join(",",addPet.getCategory())));
        assertTrue(output.contains(addPet.getLongDescription()));
        assertTrue(output.contains(addPet.getSyntax()));
    }

    @Test
    public void testExec_invalidCommandName_printCommandMessage() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        helpCommand.setCommands(commands);

        helpCommand.exec("c/unknown");

        String output = outContent.toString();
        assertTrue(output.contains("Command \"unknown\" not found"));
    }

    @Test
    public void testExec_invalidSyntax_invalidSyntaxMessage() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("help", new HelpCommand());
        helpCommand.setCommands(commands);

        helpCommand.exec("invalid");

        String output = outContent.toString();
        assertTrue(output.contains("Invalid Syntax"));
        assertTrue(output.contains("help [c/COMMAND_NAME]"));
    }

    @Test
    public void testExec_noCommandsSet_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> helpCommand.setCommands(null));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
