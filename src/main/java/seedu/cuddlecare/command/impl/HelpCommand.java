package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.command.Command;

import java.util.Map;

public class HelpCommand implements Command {

    private Map<String, Command> commands;

    public void setCommands(Map<String, Command> commands) {
        assert commands != null : "Commands cannot be null";
        this.commands = commands;
    }

    public void exec(String args) {
        printAllCommands();
    }

    private void printAllCommands() {
        System.out.println("Here is the list of all commands supported by the application: ");
        for (Map.Entry<String, Command> command: commands.entrySet()) {
            System.out.printf("%s%s: %s%n", command.getKey(), "hi");
        }
    }
}
