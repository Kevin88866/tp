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
     * @param args arguments that contain p/, n/, dt/, t/
     */
    @Override
    public void exec(String args) {
        try {
            int petIndex = -1;
            String name = null;
            LocalDate date = null;
            String type = null;

            String[] parts = args.split(" ");

            for (String part : parts) {
                if (part.startsWith("p/")) {
                    petIndex = Integer.parseInt(part.substring(2)) - 1;
                } else if (part.startsWith("n/")) {
                    name = part.substring(2);
                } else if (part.startsWith("d/")) {
                    date = LocalDate.parse(part.substring(2));
                } else if (part.startsWith("t/")) {
                    type = part.substring(2);
                }
            }

            if (petIndex < 0 || name == null || date == null || type == null) {
                System.out.println("Invalid input. Usage: add-treatment p/1 n/Rabies d/2025-10-06 t/Vaccination");
                return;
            }

            Pet pet = pets.getPetByIndex(petIndex);
            if (pet == null) {
                System.out.println("No pet found with that index.");
                return;
            }

            pet.addTreatment(new Treatment(name, date, type));
            System.out.println("Treatment \"" + name + "\" added for " + pet.getName() + ".");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid pet index. Please enter a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the treatment.");
        }
    }
}
