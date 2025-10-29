package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.command.Command;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// @@author HarshitSrivastavaHS
/**
 * A command that displays information about other commands in the application.
 * <p>
 * The HelpCommand can:
 * <ul>
 *     <li>Print all available commands, grouped by category.</li>
 *     <li>Print detailed information for a specific command using the optional 'c/COMMAND_NAME' argument.</li>
 * </ul>
 * <p>
 * It requires a map of all commands to be set via {@link #setCommands(Map)} before execution.
 * This class implements the {@link Command} interface and provides syntax, short description,
 * long description, and category metadata for the help command.
 */
public class HelpCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(HelpCommand.class.getName());

    private static final String SYNTAX = "help [c/COMMAND_NAME]";
    private static final String SHORT_DESCRIPTION = "Displays All Commands";
    private static final String LONG_DESCRIPTION = "Displays all commands in " +
            "the application, organized by category. " +
            "Optionally, it can show detailed information for a specific command.";
    private static final List<String> CATEGORIES = List.of("General");

    private Map<String, Command> commandsMap;

    /**
     * Sets the map of commands that this HelpCommand will display.
     *
     * @param commands a non-null map where the key is the command name and the value is the Command object
     */
    public void setCommands(Map<String, Command> commands) {
        assert commands != null : "Commands cannot be null";
        this.commandsMap = commands;
    }

    /**
     * Executes the help command.
     * <p>
     * Behavior:
     * <ul>
     *     <li>If no arguments are provided, prints all commands grouped by category.</li>
     *     <li>If the argument 'c/COMMAND_NAME' is provided, prints detailed info for that command.</li>
     *     <li>If invalid syntax is used, prints the syntax for the help command.</li>
     * </ul>
     *
     * @param args optional arguments for the help command
     */
    @Override
    public void exec(String args) {
        LOGGER.log(Level.INFO, "Help command executed");

        String commandName = getCommandName(args);
        if (!args.isEmpty() && commandName == null) {
            System.out.printf("Invalid Syntax.\n%s%n", SYNTAX);
            LOGGER.log(Level.INFO, "Invalid Syntax");
            return;
        }

        if (commandName != null && !commandsMap.containsKey(commandName.toLowerCase())) {
            System.out.printf("Command \"%s\" not found. Run \"help\" for " +
                    "a list of all available commands.%n", commandName);
            LOGGER.log(Level.INFO, "No command found by the name of \""+commandName+"\"");
            return;
        }

        if (commandName!=null) {
            LOGGER.log(Level.INFO, "Printing help for a command");
            printCommand(commandName, commandsMap.get(commandName.toLowerCase()));
        } else {
            LOGGER.log(Level.INFO, "Printing all the commands");
            printAllCommands();
        }
    }

    // @@author HarshitSrivastavaHS
    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public String getLongDescription() {
        return LONG_DESCRIPTION;
    }

    @Override
    public String getShortDescription() {
        return SHORT_DESCRIPTION;
    }

    @Override
    public List<String> getCategory() {
        return CATEGORIES;
    }

    private String getCommandName(String args) {
        return getTagValue(args, "c");
    }

    private String getTagValue(String args, String tag) {
        String [] parts = args.split("  (?=[\\w+]/)");
        for (String part: parts) {
            if (part.startsWith(tag)) {
                return part.substring(tag.length()+1);
            }
        }
        return null;
    }

    private void printAllCommands() {

        Map<String, Map<String, Command>> categorisedCommands = getCategorizedCommands();

        LOGGER.log(Level.INFO, "Printing all command categories with commands");

        System.out.println("Here is the list of all commands supported by the application: ");

        List<String> categoryOrder = List.of("General", "Pet", "Treatment");

        for (String category : categoryOrder) {
            if (categorisedCommands.containsKey(category)) {
                printCommandsByCategory(category, categorisedCommands.get(category));
            }
        }

        categorisedCommands.entrySet().stream()
                .filter(category -> !categoryOrder.contains(category.getKey()))
                .sorted(Map.Entry.comparingByKey())
                .forEach(category -> printCommandsByCategory(category.getKey(), category.getValue()));


        System.out.printf("Run \"%s\" to find out more about a command.%n", SYNTAX);
    }

    private void printCommandsByCategory(String category, Map<String, Command> commandsOfCategory) {
        System.out.println(category);

        commandsOfCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        System.out.printf("\t%s: %s%n", entry.getKey(), entry.getValue().getShortDescription()));

        System.out.println();
    }

    private void printCommand(String commandName, Command command) {
        System.out.printf("\tCommand Name: %s%n\tCategory: %s%n\tDescription: %s%n" +
                "\tSyntax: %s%n%n\t*[t/tag] means tag is an optional argument." +
                "%n", commandName, String.join(",", command.getCategory()) ,
                command.getLongDescription(), command.getSyntax());
    }

    private Map<String, Map<String, Command>> getCategorizedCommands() {
        Stream<Map.Entry<String, Map.Entry<String, Command>>> stream = commandsByCategory();
        return categorizeCommands(stream);
    }


    private Stream<Map.Entry<String, Map.Entry<String, Command>>> commandsByCategory() {
        return commandsMap.entrySet().stream()
                .flatMap(command ->
                        command.getValue().getCategory().stream()
                                .map(cat -> Map.entry(cat, command))
                );
    }

    private Map<String, Map<String, Command>> categorizeCommands(Stream<Map.Entry<String,
            Map.Entry<String, Command>>> stream) {
        return stream.collect(Collectors.groupingBy(
                Map.Entry::getKey,
                Collectors.toMap(
                        e -> e.getValue().getKey(),
                        e -> e.getValue().getValue(),
                        (existing, replacement) -> existing
                )
        ));
    }
}
