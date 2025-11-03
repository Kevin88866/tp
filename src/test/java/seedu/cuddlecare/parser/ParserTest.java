package seedu.cuddlecare.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.CommandWithArguments;
import seedu.cuddlecare.command.impl.ByeCommand;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ParserTest {

    private Parser parser;
    private Map<String, Command> commands;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        parser = new Parser();
        commands = new HashMap<>();
        commands.put("bye", new ByeCommand());
        parser.setCommands(commands);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void parse_knownCommand_returnsCommandWithArguments() {
        CommandWithArguments result = (CommandWithArguments) parser.parse("bye arg1 arg2");
        assertNotNull(result);
        assertInstanceOf(ByeCommand.class, result.getCommand());
        assertEquals("arg1 arg2", result.getArgs());
    }

    @Test
    void parse_commandNameCaseInsensitive() {
        CommandWithArguments result = (CommandWithArguments) parser.parse("BYE someargs");
        assertNotNull(result);
        assertInstanceOf(ByeCommand.class, result.getCommand());
        assertEquals("someargs", result.getArgs());
    }

    @Test
    void parse_emptyInput_returnsNullAndPrintsMessage() {
        Command result = parser.parse("   ");
        assertNull(result);
        String output = outContent.toString();
        assertTrue(output.contains("Empty Command"));
    }

    @Test
    void parse_invalidCommand_returnsNullAndPrintsMessage() {
        Command result = parser.parse("unknown");
        assertNull(result);
        String output = outContent.toString();
        assertTrue(output.contains("Invalid Command: unknown"));
    }

    @Test
    void parse_inputWithPipe_replacesWithSpace() {
        CommandWithArguments result = (CommandWithArguments) parser.parse("bye arg1|arg2");
        assertNotNull(result);
        assertEquals("arg1 arg2", result.getArgs());
    }
}
