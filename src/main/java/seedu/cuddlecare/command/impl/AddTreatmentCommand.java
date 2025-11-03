
package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A command that adds a treatment record for a specified pet.
 * <p>
 * When executed, this command adds a treatment to specified pet with
 * name of treatment, date of treatment and type of treatment.
 */
public class AddTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddTreatmentCommand.class.getName());

    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "add-treatment n/PET_NAME t/TREATMENT_NAME d/DATE [note/NOTE]";
    private static final String SHORT_DESCRIPTION = "Adds a treatment record for a pet";
    private static final String LONG_DESCRIPTION = "Creates and logs a new treatment " +
            "for the specified pet, including its name, date, and optional note. " +
            "Treatment name can be max 50characters long, and " +
            "the treatment date can be max 100 years in " +
            "the future and max 10 years in the past.";
    private static final List<String> CATEGORIES = List.of("Treatment");
    // @@author

    private static final int MAX_TREATMENT_NAME_LENGTH = 50;
    private static final int MAX_FUTURE_YEARS = 100;
    private static final String NAME_PATTERN = "[a-zA-Z\\- ]+";

    /**
     * A list of all pets.
     */
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
     * Parses user input, finds the corresponding pet by index,
     * creates a new Treatment object, and adds it to the pet.
     *
     * @param args arguments that contain name of pet, treatment name, date, and note (if any).
     */
    @Override
    public void exec(String args) {
        assert args != null : "Command arguments cannot be null";
        LOGGER.log(Level.INFO, "Executing add-treatment: {0}", args);

        try {

            String petName = null;
            String treatmentName = null;
            String note = null;
            LocalDate date = null;

            int noteIndex = args.indexOf("note/");
            if (noteIndex != -1) {
                note = args.substring(noteIndex + 5).trim();

                // Check if note/ is present but empty, throw error
                if (note.isEmpty()) {
                    throw new IllegalArgumentException("Error: Note cannot be empty when 'note/' is provided. " +
                            "Either provide a note or omit 'note/' entirely.");
                }

                args = args.substring(0, noteIndex).trim();
            }


            String[] parts = args.split("(?=n/|t/|d/)");

            for (String part : parts) {
                if (part.startsWith("n/")) {
                    petName = extractValue(part, "n/", "Pet name");
                } else if (part.startsWith("t/")) {
                    treatmentName = extractValue(part, "t/", "Treatment name");
                } else if (part.startsWith("d/")) {
                    String dateString = extractValue(part, "d/", "Date");
                    date = LocalDate.parse(dateString);
                    validateTreatmentDate(date);
                }
            }
            validateRequiredParameters(petName, treatmentName, date);
            Pet pet = findPet(petName);
            addTreatmentToPet(pet, treatmentName, note, date);
            printSuccessMessage(petName, treatmentName, date, note);

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid arguments: {0}", e.getMessage());
            if (e.getMessage() != null && !e.getMessage().isEmpty()) {
                System.out.println(e.getMessage());
            }
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.WARNING, "Invalid date format", e);
            System.out.println("Invalid date format. Please use yyyy-MM-dd format (e.g., 2024-12-25).");
        }
    }

    // @@author HarshitSrivastavaHS
    @Override
    public String getSyntax() {
        return SYNTAX;
    }

    @Override
    public String getLongDescription() {
        return LONG_DESCRIPTION;
    }

    @Override
    public String getShortDescription() {
        return SHORT_DESCRIPTION;
    }

    @Override
    public List<String> getCategory() {
        return CATEGORIES;
    }
    // @@author

    /**
     * Extracts and validates a parameter value from a command part.
     *
     * @param part the command part containing the parameter
     * @param prefix the parameter prefix (e.g., "n/", "t/")
     * @param fieldName the name of the field for error messages
     * @return the extracted value
     * @throws IllegalArgumentException if the value is empty or invalid
     */
    private String extractValue(String part, String prefix, String fieldName) {
        String value = part.substring(prefix.length()).trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException("Error: " + fieldName + " cannot be empty.");
        }

        // Validate treatment name specifically
        if (prefix.equals("t/")) {
            validateTreatmentName(value);
        }

        return value;
    }

    /**
     * Validates the treatment name according to business rules.
     *
     * @param treatmentName the treatment name to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateTreatmentName(String treatmentName) {
        // Check length
        if (treatmentName.length() > MAX_TREATMENT_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Error: Treatment name cannot exceed " + MAX_TREATMENT_NAME_LENGTH + " characters. " +
                            "Current length: " + treatmentName.length());
        }

        // Check for valid characters
        if (!treatmentName.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException(
                    "Error: Treatment name can only contain letters (A-Z, a-z), hyphens (-), and spaces. " +
                            "Invalid characters found in: \"" + treatmentName + "\"");
        }
    }

    /**
     * Validates that the treatment date is not more than 100 years in the future.
     *
     * @param date the date to validate
     * @throws IllegalArgumentException if date is too far in the future
     */
    private void validateTreatmentDate(LocalDate date) {
        LocalDate maxFutureDate = LocalDate.now().plusYears(MAX_FUTURE_YEARS);

        if (date.isAfter(maxFutureDate)) {
            throw new IllegalArgumentException(
                    "Error: Treatment date cannot be more than " + MAX_FUTURE_YEARS +
                            " years in the future. " +
                            "Provided date: " + date + ", Maximum allowed: " + maxFutureDate);
        }
    }

    /**
     * Validates that all required parameters are present.
     *
     * @param petName the pet name
     * @param treatmentName the treatment name
     * @param date the treatment date
     * @throws IllegalArgumentException if any required parameter is missing
     */
    private void validateRequiredParameters(String petName, String treatmentName, LocalDate date) {
        if (petName == null || treatmentName == null || date == null) {
            LOGGER.log(Level.INFO, "Missing required parameters - petName: {0}, treatmentName: {1}, date: {2}",
                    new Object[]{petName, treatmentName, date});
            Ui.printInvalidInputMessage(SYNTAX);
            throw new IllegalArgumentException();
        }
    }

    /**
     * Finds a pet by name.
     *
     * @param petName the name of the pet to find
     * @return the Pet object
     * @throws IllegalArgumentException if the pet is not found
     */
    private Pet findPet(String petName) {
        Pet pet = pets.getPetByName(petName);

        if (pet == null) {
            throw new IllegalArgumentException("Pet not found: " + petName);
        }

        return pet;
    }

    /**
     * Checks if a duplicate treatment already exists for the pet.
     *
     * @param pet the pet to check
     * @param treatmentName the treatment name
     * @param date the treatment date
     * @return true if duplicate exists, false otherwise
     */
    private boolean isDuplicateTreatment(Pet pet, String treatmentName, LocalDate date) {
        for (Treatment treatment : pet.getTreatments()) {
            if (treatment.getName().equalsIgnoreCase(treatmentName) &&
                    treatment.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a treatment to the specified pet.
     * Checks for duplicates before adding.
     *
     * @param pet the pet to add the treatment to
     * @param treatmentName the treatment name
     * @param note the treatment note
     * @param date the treatment date
     * @throws IllegalArgumentException if duplicate treatment exists
     */
    private void addTreatmentToPet(Pet pet, String treatmentName, String note, LocalDate date) {
        // Check for duplicate before adding
        if (isDuplicateTreatment(pet, treatmentName, date)) {
            LOGGER.log(Level.INFO, "Duplicate treatment '{0}' on {1} for {2}",
                    new Object[]{treatmentName, date, pet.getName()});
            throw new IllegalArgumentException(
                    "Duplicate treatment: \"" + treatmentName + "\" on " + date +
                            " already exists for " + pet.getName() + ".");
        }

        Treatment newTreatment = new Treatment(treatmentName, note, date);
        pet.addTreatment(newTreatment);
        LOGGER.log(Level.INFO, "Added treatment '{0}' for {1} on {2}",
                new Object[]{treatmentName, pet.getName(), date});
    }

    /**
     * Prints the success message after adding a treatment.
     * @param petName the pet name
     * @param treatmentName the treatment name
     * @param date the treatment date
     * @param note note attached to treatment
     */
    private void printSuccessMessage(String petName, String treatmentName, LocalDate date, String note) {
        boolean hasNote = note != null && !note.isEmpty();
        String noteFormat = hasNote ? ("\n  Note: " + note) : "";
        System.out.println("Added treatment \"" + treatmentName + "\" on " + date +
                " for " + petName + "." + noteFormat);
    }
}
