package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to delete a pet from the {@link PetList} based on its index.
 * <p>
 * Usage: <code>delete-pet i/&lt;index&gt;</code>
 * <ul>
 *     <li>i/&lt;index&gt; : the 1-based index of the pet to delete from the list</li>
 * </ul>
 * <p>
 * Example: <code>delete-pet i/2</code> will remove the second pet in the list.
 */
public class DeletePetCommand implements Command {

    /**
     * Logger instance for the {@code DeletePetCommand} class.
     */
    private static final Logger logger = Logger.getLogger(DeletePetCommand.class.getName());
    static {
        logger.setLevel(Level.WARNING);
    }

    /**
     * Syntax help message displayed on incorrect usage.
     */
    private static final String SYNTAX = "delete-pet i/<index>";

    /**
     * The list of pets to operate on.
     */
    private final PetList pets;

    /**
     * Constructs a new DeletePetCommand for the specified pet list.
     *
     * @param pets the PetList from which a pet will be deleted
     */
    public DeletePetCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the delete-pet command.
     * <p>
     * Steps performed by this method:
     * <ol>
     *     <li>Parses the index from the input arguments using {@link #parseIndex(String)}.</li>
     *     <li>Validates the index using {@link #isValidIndex(int)}.</li>
     *     <li>If valid, deletes the pet using {@link #deletePet(int)} and prints a confirmation message.</li>
     *     <li>If invalid, prints an error message indicating incorrect syntax or invalid index.</li>
     * </ol>
     *
     * @param args the command arguments in the form <code>i/&lt;index&gt;</code>
     */
    public void exec(String args) {
        logger.finest(() -> "Executing DeletePetCommand with args: " + args);

        try {
            int index = parseIndex(args);
            if (index == -1) {
                System.out.printf("Incorrect Syntax: %s%n", SYNTAX);
                logger.fine(() -> "Failed to parse index from args: " + args);
                return;
            }

            if (!isValidIndex(index)) {
                System.out.printf("Invalid pet index: %d. Total pets: %d%n", index, pets.size());
                logger.fine(() -> "Invalid index provided: " + index);
                return;
            }
            deletePet(index);
        } catch (NumberFormatException e) {
            System.out.printf("Incorrect syntax: %s%n", SYNTAX);
            logger.fine(() -> "NumberFormatException while parsing index: " + e.getMessage());
        }
    }

    /**
     * Parses the pet index from the command arguments.
     *
     * @param args the input arguments
     * @return the parsed 1-based index if present, or -1 if missing
     * @throws NumberFormatException if the index is not a valid integer
     */
    private int parseIndex(String args) throws NumberFormatException {
        String[] parts = args.split(" (?=[w+]/)");
        for (String part : parts) {
            if (part.startsWith("i/")) {
                return Integer.parseInt(part.substring(2).trim());
            }
        }
        return -1;
    }

    /**
     * Checks whether the provided index is within bounds of the pet list.
     *
     * @param index the 1-based pet index
     * @return true if the index is valid, false otherwise
     */
    private boolean isValidIndex(int index) {
        return index > 0 && index <= pets.size();
    }

    /**
     * Deletes the pet at the specified index from the pet list and prints a confirmation message.
     *
     * @param index the 1-based index of the pet to delete
     */
    private void deletePet(int index) {
        Pet deleted = pets.deleteByIndex(index - 1);
        assert deleted != null : "Deleted pet should never be null";

        System.out.printf("Successfully removed %s (%s, %d) from the list.%n",
                deleted.getName(), deleted.getSpecies(), deleted.getAge());

        logger.info(() -> String.format("Deleted pet at index %d: %s (%s, %d)",
                index, deleted.getName(), deleted.getSpecies(), deleted.getAge()));
    }
}
