package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A command that adds a treatment record for a specified pet.
 *
 * When executed, this command adds a treatment to specified pet with
 * name of treatment, date of treatment and type of treatment.
 */
public class AddTreatmentCommand implements Command {

    /** A list of all pets. */
    private final PetList pets;
    private static final Logger logger = Logger.getLogger(AddTreatmentCommand.class.getName());

    /**
     * Initializes the AddTreatmentCommand with the list of pets.
     *
     * @param pets the list of all pets
     */
    public AddTreatmentCommand(PetList pets) {
        assert pets != null : "PetList cannot be null";
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
        assert args != null : "Command arguments cannot be null";
        logger.log(Level.INFO, "Executing add-treatment: {0}", args);

        String petName = null;
        String treatmentName = null;
        LocalDate date = null;

        try {
            String[] parts = args.split("(?=n/|t/|d/)");
            for (String part : parts) {
                if (part.startsWith("n/")) {
                    petName = part.substring(2).trim();
                    assert !petName.isEmpty() : "Pet name should not be empty";
                } else if (part.startsWith("t/")) {
                    treatmentName = part.substring(2).trim();
                    assert !treatmentName.isEmpty() : "Treatment name should not be empty";
                } else if (part.startsWith("d/")) {
                    String dateString = part.substring(2).trim();
                    assert !dateString.isEmpty() : "Date string should not be empty";

                    try {
                        date = LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        logger.log(Level.WARNING, "Invalid date format: {0}", dateString);
                        System.out.println("Invalid date format. Please use yyyy-MM-dd format (e.g., 2024-12-25).");
                        return;
                    }
                }
            }

            if (petName == null || treatmentName == null || date == null) {
                System.out.println("Invalid input. Usage: add-treatment n/PET_NAME t/TREATMENT_NAME d/DATE");
                return;
            }

            // Find the pet
            Pet pet = pets.getPetByName(petName);
            if (pet == null) {
                System.out.println("Pet not found: " + petName);
                return;
            }

            Treatment newTreatment = new Treatment(treatmentName, date);
            ArrayList<Treatment> treatments = pet.getTreatments();
            treatments.add(newTreatment);

            logger.log(Level.INFO, "Added treatment '{0}' for {1} on {2}",
                    new Object[]{treatmentName, petName, date});
            System.out.println("Added treatment \"" + treatmentName + "\" on " + date +
                    " for " + petName + ".");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to add treatment", e);
            System.out.println("Unable to add the treatment. Please try again.");
        }
    }
}
