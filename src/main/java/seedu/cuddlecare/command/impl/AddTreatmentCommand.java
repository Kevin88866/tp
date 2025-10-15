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

    private static final Logger LOGGER = Logger.getLogger(AddTreatmentCommand.class.getName());

    /** A list of all pets. */
    private final PetList pets;

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
        LOGGER.log(Level.INFO, "Executing add-treatment: {0}", args);

        String petName = null;
        String treatmentName = null;
        LocalDate date = null;

        try {
            String[] parts = args.split("(?=n/|t/|d/)");
            for (String part : parts) {
                if (part.startsWith("n/")) {
                    petName = part.substring(2).trim();
                    if (petName.isEmpty()) {
                        System.out.println("Error: Pet name cannot be empty.");
                        return;
                    }
                } else if (part.startsWith("t/")) {
                    treatmentName = part.substring(2).trim();
                    if (treatmentName.isEmpty()) {
                        System.out.println("Error: Treatment name cannot be empty.");
                        return;
                    }
                } else if (part.startsWith("d/")) {
                    String dateString = part.substring(2).trim();
                    if (dateString.isEmpty()) {
                        System.out.println("Error: Date cannot be empty.");
                        return;
                    }

                    try {
                        date = LocalDate.parse(dateString);
                    } catch (DateTimeParseException e) {
                        LOGGER.log(Level.WARNING, "Invalid date format: {0}", dateString);
                        System.out.println("Invalid date format. Please use yyyy-MM-dd format (e.g., 2024-12-25).");
                        return;
                    }
                }
            }

            if (petName == null || treatmentName == null || date == null) {
                LOGGER.log(Level.INFO, "Missing required parameters - petName: {0}, treatmentName: {1}, date: {2}",
                        new Object[]{petName, treatmentName, date});

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

            LOGGER.log(Level.INFO, "Added treatment '{0}' for {1} on {2}",
                    new Object[]{treatmentName, petName, date});
            System.out.println("Added treatment \"" + treatmentName + "\" on " + date +
                    " for " + petName + ".");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unable to add treatment", e);
            System.out.println("Unable to add the treatment. Please try again.");
        }
    }
}
