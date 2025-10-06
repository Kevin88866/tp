package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

/**
 * A command that adds a pet to the pet list.
 *
 * When executed, this command adds a pet with its
 * name, species, and age added to its attributes.
 */
public class AddPetCommand implements Command {
    
    /** A list of all pets. */
    private final PetList pets;
    
    /**
     * Initializes the AddPetCommand with the
     * list of pets to be modified.
     * 
     * @param pets the list of all pets
     */
    public AddPetCommand(PetList pets) {
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
        try {
            String[] parts = args.split(" ");

            String name = null;
            String species = null;
            int age = -1;

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    name = part.substring(2);
                } else if (part.startsWith("s/")) {
                    species = part.substring(2);
                } else if (part.startsWith("a/")) {
                    age = Integer.parseInt(part.substring(2));
                }
            }

            if (name == null || species == null || age < 0) {
                System.out.println("Invalid input. Please try again.");
                return;
            }

            Pet newPet = new Pet(name, species, age);
            if (!pets.add(newPet)) {
                System.out.println("A pet with that name already exists.");
                return;
            }
            System.out.println(name + " has been successfully added.");
        } catch (NumberFormatException e) {
            System.out.println("Age must be a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    } 
}
