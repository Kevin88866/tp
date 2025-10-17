package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * A command that deletes a treatment record for a specified pet.
 *
 * Format: delete-treatment n/PET_NAME i/INDEX
 */
public class DeleteTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteTreatmentCommand.class.getName());
    
    private final PetList pets;

    /**
     * Creates a new DeleteTreatmentCommand with the given PetList.
     *
     * @param pets the list of all pets in the system
     */
    public DeleteTreatmentCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
        this.pets = pets;
    }

    /**
     * Executes the delete-treatment command.
     *
     * Finds the specified pet by name, removes treatment at the given index
     * from that pet’s treatment list, and confirms deletion.
     *
     * @param args the command arguments
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";
        LOGGER.log(Level.INFO, "Executing delete-treatment: {0}", args);

        String petName = null;
        int index = -1;

        try {

            String[] parts = args.split("(?=n/|i/)");

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    petName = part.substring(2).trim();
                    if (petName.isEmpty()) {
                        System.out.println("Error: Pet name cannot be empty.");
                        return;
                    }
                } else if (part.startsWith("i/")) {
                    String indexString = part.substring(2).trim();
                    if (indexString.isEmpty()) {
                        System.out.println("Error: Index cannot be empty.");
                        return;
                    }

                    try {
                        index = Integer.parseInt(indexString) - 1;
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.WARNING, "Invalid index format: {0}", indexString);
                        System.out.println("Invalid index format. Must be an integer.");
                        return;
                    }
                }
            }

            if (petName == null || index == -1) {
                System.out.println("Invalid input. Usage: delete-treatment n/PET_NAME i/INDEX");
                return;
            }

            Pet pet = pets.getPetByName(petName);
            if (pet == null) {
                LOGGER.log(Level.INFO, "Pet not found: {0}", petName);
                System.out.println("Pet not found: " + petName);
                return;
            }

            ArrayList<Treatment> treatments = pet.getTreatments();
            assert treatments != null : "Treatments list should not be null";

            if (treatments.isEmpty()) {
                System.out.println(petName + " has no treatments to delete.");
                return;
            }

            if (index < 0 || index >= treatments.size()) {
                System.out.println("Invalid treatment index. Please check 'list-treatments n/" + petName + "'.");
                return;
            }

            Treatment removed = treatments.remove(index);
            LOGGER.log(Level.INFO, "Deleted treatment '{0}' from {1}", new Object[]{removed.getName(), petName});
            System.out.println("Deleted treatment \"" + removed.getName() + "\" for " + petName + ".");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unable to delete treatment", e);
            System.out.println("Unable to delete the treatment. Please try again.");
        }
    }
}
