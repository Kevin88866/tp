package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.util.ArrayList;

/**
 * A command that deletes a treatment record for a specified pet.
 *
 * Command format: delete-treatment n/PET_NAME i/INDEX
 */
public class DeleteTreatmentCommand implements Command {

    private final PetList pets;

    /**
     * Creates a new DeleteTreatmentCommand with the given PetList.
     *
     * @param pets the list of all pets in the system
     */
    public DeleteTreatmentCommand(PetList pets) {
        this.pets = pets;
    }

    /**
     * Executes the delete-treatment command.
     *
     * Finds the specified pet by name, removes the treatment at the given index
     * from that pet’s treatment list, and confirms deletion.
     *
     * @param args the command arguments (expected format: n/PET_NAME i/INDEX)
     */
    @Override
    public void exec(String args) {
        String petName = null;
        int index = -1;

        // Defensive parsing: handles extra spaces gracefully
        String[] parts = args.trim().split("\\s+");
        for (String part : parts) {
            if (part.startsWith("n/")) {
                petName = part.substring(2).trim();
            } else if (part.startsWith("i/")) {
                try {
                    index = Integer.parseInt(part.substring(2).trim()) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index format. Must be an integer.");
                    return;
                }
            }
        }

        if (petName == null || index < 0) {
            System.out.println("Invalid input. Usage: delete-treatment n/PET_NAME i/INDEX");
            return;
        }

        Pet pet = pets.getPetByName(petName);
        if (pet == null) {
            System.out.println("Pet not found: " + petName);
            return;
        }

        ArrayList<Treatment> treatments = pet.getTreatments();
        if (treatments.isEmpty()) {
            System.out.println(petName + " has no treatments to delete.");
            return;
        }

        if (index >= treatments.size()) {
            System.out.println("Invalid treatment index. Please check 'list-treatments n/" + petName + "'.");
            return;
        }

        Treatment removed = treatments.remove(index);
        System.out.println("Deleted treatment \"" + removed + "\" for " + petName + ".");
    }
}

