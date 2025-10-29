package seedu.cuddlecare.parser;

import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.CommandWithArguments;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input strings into {@link Command} objects.
 * <p>
 * The parser maps command names to command objects and can
 * handle commands with optional arguments.
 */
public class Parser {

    /**
     * Logger instance for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    static {
        LOGGER.setLevel(Level.OFF);
    }


    /**
     * Map of available commands keyed by their string representation.
     */
    private Map<String, Command> commands;

    /**
     * Sets the available commands for this parser.
     *
     * @param commands Map of command names to their corresponding {@link Command} objects
     */
    public void setCommands(Map<String, Command> commands) {
        assert commands != null : "commands map cannot be null";
        this.commands = commands;
        LOGGER.log(Level.INFO, "Commands map has been set with " + commands.size() + " entries.");
    }

    /**
     * Parses a user input string into a {@link Command} object.
     * <p>
     * If the input is empty or the command name is invalid,
     * prints an error message and returns {@code null}.
     *
     * @param input The user input string
     * @return A {@link CommandWithArguments} wrapping the corresponding {@link Command},
     * or {@code null} if the input is empty or invalid
     */
    public Command parse(String input) {
        assert input != null : "input cannot be null";
        input = input.trim();

        if (input.isEmpty()) {
            System.out.println("Empty Command");
            LOGGER.log(Level.WARNING, "Received empty input");
            return null;
        }

        String[] commandParts = input.split(" ", 2);
        String commandName = commandParts[0];
        String commandArguments = commandParts.length > 1 ? commandParts[1] : "";

        Command command = commands.get(commandName.toLowerCase());

        if (command == null) {
            System.out.printf("Invalid Command: %s%nRun \"help\" to " +
                    "find the list of all available commands.%n", commandName);
            LOGGER.log(Level.WARNING, "Invalid command received: " + commandName);
            return null;
        }

        LOGGER.log(Level.INFO, "Parsed command: " + commandName + " with arguments: " + commandArguments);
        return new CommandWithArguments(command, commandArguments);
    }
}
