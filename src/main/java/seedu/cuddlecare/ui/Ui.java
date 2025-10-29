package seedu.cuddlecare.ui;

import java.util.ArrayList;

public class Ui {

    /**
     * Symbol used to prompt user input.
     */
    private static final String PROMPT_SYMBOL = "> ";

    private Ui() {

    }

    /**
     * Prints a greeting message to the user.
     */
    public static void printGreetMessage() {
        System.out.println("Hello! Welcome to CuddleCare.");
    }

    /**
     * Prints a bye message to the user.
     */
    public static void printByeMessage() {
        System.out.println("Bye bye, Have a wonderful day ahead :)");
    }

    /**
     * Prints the input prompt to the user.
     */
    public static void printInputPrompt() {
        System.out.print(PROMPT_SYMBOL);
    }

    /**
     * Prints an invalid input message.
     *
     * @param syntax the syntax for the command
     */
    public static void printInvalidInputMessage(String syntax) {
        System.out.println("Invalid input. Usage: " + syntax);
    }

    /**
     * Prints a message to indicate PetList is empty.
     */
    public static void printEmptyPetListMessage() {
        System.out.println("No pets found.");
    }

    /**
     * Prints a header message to the user.
     *
     * @param header the header message to be printed
     */
    public static void printHeader(String header) {
        System.out.println(header);
    }

    /**
     * Prints the elements of the given List to the user,
     * each prefixed with its index number starting from 1.
     *
     * @param list the list of elements to be printed
     * @param <T> the type of elements contained in the list
     */
    public static <T> void printList(ArrayList<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + "." + list.get(i));
        }
    }


    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void printMarkUsage() {
        System.out.println("Usage: mark n/PET_NAME i/INDEX");
        System.out.println("Example: mark n/Milo i/2");
    }

    public static void printUnmarkUsage() {
        System.out.println("Usage: unmark n/PET_NAME i/INDEX");
        System.out.println("Example: unmark n/Milo i/2");
    }

    public static void printEditPetUsage() {
        System.out.println("Usage: edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]");
    }

}
