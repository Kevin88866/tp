package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A command that lists all treatment records for a specified pet.
 * <p>
 * When executed, this command finds the specified {@link Pet} in the
 * {@link PetList}, gets the pet's treatment list and prints the
 * treatment records.
 */
public class ListPetTreatmentsCommand implements Command {

    private static final Logger logger = Logger.getLogger(ListPetTreatmentsCommand.class.getName());

    /**
     * A list of all pets.
     */
    private final PetList pets;

    /**
     * Initializes the ListPetTreatmentsCommand with the
     * list of pets that holds all pets.
     *
     * @param pets the list of all pets
     */
    public ListPetTreatmentsCommand(PetList pets) {
        this.pets = pets;
        assert pets != null : "pets cannot be null.";
        logger.setLevel(Level.OFF);
    }

    /**
     * Executes the List Pet Treatment command.
     * <p>
     * Parses user input, finds the corresponding pet by name,
     * gets the pet's treatment list, and prints out all treatment
     * records in the treatment list.
     *
     * @param args arguments that contain name of pet
     */
    @Override
    public void exec(String args) {
        String petName;
        args = args.trim();

        if (args.startsWith("n/")) {
            petName = args.substring(2).trim();
        } else {
            logger.log(Level.WARNING, "Invalid format input.");
            System.out.println("Invalid input. Usage: list-treatments n/PET_NAME");
            return;
        }

        Pet pet = pets.getPetByName(petName);
        if (pet == null) {
            logger.log(Level.WARNING, "No pet named " + petName);
            System.out.println("Pet not found: " + petName);
            return;
        }

        ArrayList<Treatment> treatments = pet.getTreatments();
        if (treatments.isEmpty()) {
            System.out.println(petName + " has no logged treatments.");
            return;
        }
        System.out.println(petName + "'s treatment history:");
        for (int i = 0; i < treatments.size(); i++) {
            System.out.println(i + 1 + "." + treatments.get(i));
        }
    }
}
