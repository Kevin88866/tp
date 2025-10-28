package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelpCommand implements Command {

    private static final String SYNTAX = "help [c/COMMAND_NAME]";

    private Map<String, Command> commandsMap;
    
    public void setCommands(Map<String, Command> commands) {
        assert commands != null : "Commands cannot be null";
        this.commandsMap = commands;
    }


    public void exec(String args) {
        String commandName = getCommandName(args);
        if (!args.isEmpty() && commandName == null) {
            System.out.printf("Invalid Syntax.\n%s", SYNTAX);
            return;
        }

        if (commandName != null && !commandsMap.containsKey(commandName.toLowerCase())) {
            System.out.printf("Command \"%s\" not found. Run \"help\" for a list of all available commands.%n", commandName);
            return;
        }

        if (commandName!=null) {
            printCommand(commandName, commandsMap.get(commandName.toLowerCase()));
        } else {
            printAllCommands();
        }
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

        System.out.println("Here is the list of all commands supported by the application: ");
        for (Map.Entry<String, Map<String, Command>> categoryCommands: categorisedCommands.entrySet()) {
            printCommandsByCategory(categoryCommands.getKey(), categoryCommands.getValue());
        }
    }

    private void printCommandsByCategory(String category, Map<String, Command> commandsOfCategory) {
        System.out.println(category);
        for (Map.Entry<String, Command> command: commandsOfCategory.entrySet()) {
            System.out.printf("\t%s: %s%n", command.getKey(), command.getValue().getShortDescription());
        }
    }

    private void printCommand(String commandName, Command command) {
        System.out.printf("\tCommand Name: %s%n\tCategory: %s%n\tDescription: %s%n\tSyntax: %s%n%n\t*[t/tag] means tag is an optional argument.%n", commandName, String.join(",", command.getCategory()) ,command.getLongDescription(), command.getSyntax());
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

    private Map<String, Map<String, Command>> categorizeCommands(Stream<Map.Entry<String, Map.Entry<String, Command>>> stream) {
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
