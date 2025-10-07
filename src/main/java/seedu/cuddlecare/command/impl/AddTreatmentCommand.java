package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * A command that adds a treatment record for a specified pet.
 *
 * When executed, this command adds a treatment to specified pet with
 * name of treatment, date of treatment and type of treatment.
 */
public class AddTreatmentCommand implements Command {

    /** A list of all pets. */
    private final PetList pets;

    /**
     * Initializes the AddTreatmentCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public AddTreatmentCommand(PetList pets) {
        this.pets = pets;
    }

    /**
     * Executes the Add Treatment command.
     *
     * Parses user input, finds the corresponding pet by index,
     * creates a new Treatment object, and adds it to the pet.
     *
     * @param args arguments that contain name of pet, treatment name and date.
     */
    @Override
    public void exec(String args) {
        args = args.trim().replaceAll("\\s+", " ");

        String petName = null;
        String treatmentName = null;
        LocalDate date = null;

        String[] parts = args.split(" (?=[ptd]/)");

        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("n/")) {
                petName = part.substring(2).trim();
            } else if (part.startsWith("t/")) {
                treatmentName = part.substring(2).trim();
            } else if (part.startsWith("d/")) {
                try {
                    date = LocalDate.parse(part.substring(2).trim());
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    return;
                }
            }
        }

        if (petName == null || petName.isEmpty() ||
                treatmentName == null || treatmentName.isEmpty() ||
                date == null) {
            System.out.println("Invalid input. Usage: add-treatment n/PET_NAME t/TREATMENT_NAME d/DATE");
            return;
        }

        Pet pet = pets.getPetByName(petName);
        if (pet == null) {
            System.out.println("Pet not found: " + petName);
            return;
        }

        Treatment newTreatment = new Treatment(treatmentName, date);
        pet.addTreatment(newTreatment);
        System.out.println("Treatment added to " + petName + ": " + treatmentName + " on " + date);
    }
}
