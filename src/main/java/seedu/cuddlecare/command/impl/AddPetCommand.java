package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A command that adds a pet to the pet list.
 *
 * When executed, this command adds a pet with its
 * name, species, and age added to its attributes.
 */
public class AddPetCommand implements Command {
    
    /** Logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(AddPetCommand.class.getName());

    static {
        LOGGER.setLevel(Level.OFF);
    }

    /** A list of all pets. */
    private final PetList pets;
    
    /**
     * Initializes the AddPetCommand with the
     * list of pets to be modified.
     * 
     * @param pets the list of all pets
     */
    public AddPetCommand(PetList pets) {
        assert pets != null : "Pets list cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the Add Pet command.
     *
     * Parses user input, creates a new Pet object, and
     * adds it to the pet list.
     *
     * @param args arguments that contain name, species, and age
     */
    @Override
    public void exec(String args) {
        LOGGER.log(Level.INFO, "Executing add-pet command with args: " + args);
        assert args != null : "Command arguments cannot be null";

        try {
            String[] parts = args.trim().split("(?=\\bn/|\\bs/|\\ba/)");

            String name = null;
            String species = null;
            int age = -1;

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    name = part.substring(2).trim();
                } else if (part.startsWith("s/")) {
                    species = part.substring(2).trim();
                } else if (part.startsWith("a/")) {
                    age = Integer.parseInt(part.substring(2));
                }
            }

            if (name == null || species == null || age < 0) {
                LOGGER.log(Level.WARNING, "Invalid input. Missing/invalid fields.");
                System.out.println("Invalid input. Please try again.");
                return;
            }

            Pet newPet = new Pet(name, species, age);
            assert newPet != null : "New pet object should not be null.";

            if (!pets.add(newPet)) {
                LOGGER.log(Level.WARNING, "Duplicate pet created: " + name);
                System.out.println("A pet with that name already exists.");
                return;
            }

            LOGGER.log(Level.INFO, "Pet has been added: " + name);
            System.out.println(name + " has been successfully added.");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number format for age: " + args, e);
            System.out.println("Age must be a valid number.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error when executing add-pet command", e);
            System.out.println("An error occurred.");
        } finally {
            LOGGER.log(Level.INFO, "Add-pet command execution completed.");
        }
    } 
}
