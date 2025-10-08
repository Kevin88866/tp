package seedu.cuddlecare;

import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.command.impl.AddPetCommand;
import seedu.cuddlecare.command.impl.AddTreatmentCommand;
import seedu.cuddlecare.command.impl.ByeCommand;
import seedu.cuddlecare.command.impl.ListAllTreatmentsCommand;
import seedu.cuddlecare.command.impl.ListPetTreatmentsCommand;
import seedu.cuddlecare.command.impl.ListPetsCommand;
import seedu.cuddlecare.command.impl.MarkTreatmentCommand;
import seedu.cuddlecare.command.impl.UnmarkTreatmentCommand;
import seedu.cuddlecare.parser.Parser;


import java.util.Map;
import java.util.Scanner;

/**
 * The main class for the CuddleCare application.
 * <p>
 * This class is responsible for initializing the application,
 * setting up the parser and commands, greeting the user,
 * and running the main application loop.
 */
public class CuddleCare {

    /**
     * Symbol used to prompt user input.
     */
    private static final String PROMPT_SYMBOL = "> ";

    /**
     * Parser used to convert user input into commands.
     */
    private final Parser parser;

    /**
     * Map of available commands keyed by their string representation.
     */
    private Map<String, Command> commands;

    /**
     * List of all pets.
     */
    private final PetList pets = new PetList();

    /**
     * Constructs a new CuddleCare application.
     * Initializes the parser.
     */
    CuddleCare() {
        parser = new Parser();
    }

    /**
     * Starts the application by initializing commands,
     * greeting the user, and entering the main loop.
     */
    void run() {
        initialiseCommands();
        greet();
        startApplicationLoop();
    }

    /**
     * Prints a greeting message to the user.
     */
    void greet() {
        System.out.println("Hello! Welcome to CuddleCare.");
    }

    /**
     * Starts the main application loop.
     * Continuously reads user input, parses it into commands,
     * and executes them until the application is terminated.
     */
    void startApplicationLoop() {
        Scanner sc = new Scanner(System.in);
        printInputPrompt();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            Command command = parser.parse(input);
            if (command == null) {
                printInputPrompt();
                continue;
            }
            command.exec("");
            printInputPrompt();
        }
        sc.close();
    }

    /**
     * Prints the input prompt to the user.
     */
    void printInputPrompt() {
        System.out.print(PROMPT_SYMBOL);
    }

    /**
     * Initializes the available commands and registers them with the parser.
     */
    void initialiseCommands() {

        commands = Map.ofEntries(
                Map.entry("bye", new ByeCommand()),
                Map.entry("add-pet", new AddPetCommand(pets)),
                Map.entry("add-treatment", new AddTreatmentCommand(pets)),
                Map.entry("list-pets", new ListPetsCommand(pets)),
                Map.entry("mark", new MarkTreatmentCommand(pets)),
                Map.entry("unmark", new UnmarkTreatmentCommand(pets)),
                Map.entry("list-all-treatments", new ListAllTreatmentsCommand(pets)),
                Map.entry("list-treatments", new ListPetTreatmentsCommand(pets))
        );

        parser.setCommands(commands);
    }

    Map<String, Command> getCommandsForTesting() {
        return commands;
    }

    /**
     * The main entry point for the CuddleCare application.
     *
     * @param args Command-line arguments (ignored)
     */
    public static void main(String[] args) {
        CuddleCare application = new CuddleCare();
        application.run();
    }
}
