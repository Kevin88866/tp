package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.ui.Ui;

import java.util.List;

//@@author HarshitSrivastavaHS

/**
 * A command that exits the application.
 *
 * When executed, this command prints a farewell message and
 * terminates the program.
 */
public class ByeCommand implements Command {


    private static final String SYNTAX = "bye";
    private static final String SHORT_DESCRIPTION = "Exits the application";
    private static final String LONG_DESCRIPTION = "Terminates the CuddleCare application " +
            "after displaying a farewell message.";
    private static final List<String> CATEGORIES = List.of("General");

    /**
     * Executes the Bye command.
     *
     * Prints a farewell message to the console and exits the application.
     *
     * @param args ignored; ByeCommand does not use any arguments
     */
    public void exec(String args) {
        Ui.printByeMessage();
        System.exit(0);
    }

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
}
