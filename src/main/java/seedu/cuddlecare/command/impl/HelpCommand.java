package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelpCommand implements Command {

    private Map<String, Command> commandsMap;
    
    public void setCommands(Map<String, Command> commands) {
        assert commands != null : "Commands cannot be null";
        this.commandsMap = commands;
    }


    public void exec(String args) {
        printAllCommands();
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
            printCommand(command.getKey(), command.getValue());
        }
    }

    private void printCommand(String commandName, Command command) {
        System.out.printf("\t%s: %s%n", commandName, command.getShortDescription());
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
