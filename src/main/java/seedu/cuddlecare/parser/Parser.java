package seedu.cuddlecare.parser;

import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.CommandWithArguments;

import java.util.Map;

/**
 * Parses user input strings into {@link Command} objects.
 *
 * The parser maps command names to command objects and can
 * handle commands with optional arguments.
 */
public class Parser {

    /** Map of available commands keyed by their string representation. */
    private Map<String, Command> commands;

    /**
     * Sets the available commands for this parser.
     *
     * @param commands Map of command names to their corresponding {@link Command} objects
     */
    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Parses a user input string into a {@link Command} object.
     *
     * If the input is empty or the command name is invalid,
     * prints an error message and returns {@code null}.
     *
     * @param input The user input string
     * @return A {@link CommandWithArguments} wrapping the corresponding {@link Command},
     *         or {@code null} if the input is empty or invalid
     */
    public Command parse(String input) {
        input = input.trim();

        if (input.isEmpty()) {
            System.out.println("Empty Command");
            return null;
        }

        for (String cmd : commands.keySet()) {
            if (input.toLowerCase().startsWith(cmd.toLowerCase())) {
                String args = input.substring(cmd.length()).trim();
                return new CommandWithArguments(commands.get(cmd), args);
            }
        }

        System.out.println("Invalid Command: " + input);
        return null;
    }
}
