package seedu.cuddlecare.command.impl;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

/**
 * Unmarks (sets not completed) a treatment for a specific pet by local index.
 * <p>Usage: {@code unmark n/PET_NAME i/INDEX}</p>
 * Example: {@code unmark n/Milo i/2}
 */
public class UnmarkTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(UnmarkTreatmentCommand.class.getName());

    /** Repository of pets. */
    private final PetList pets;

    /**
     * @param pets repository of pets
     */
    public UnmarkTreatmentCommand(PetList pets) {
        this.pets = pets;
    }

    /**
     * Executes the unmark operation.
     * Expected args format: n/PET_NAME i/INDEX (1-based index within the pet's treatments)
     *
     * @param args raw argument string from parser
     */
    @Override
    public void exec(String args) {
        assert pets != null : "Pet list must not be null";
        Parsed parsed = parseArgs(args);
        if (!parsed.valid) {
            printUsage();
            return;
        }

        String petName = parsed.petName;
        int index1Based = parsed.index;

        Pet pet = pets.getPetByName(petName);
        if (pet == null) {
            System.out.println("Pet not found: " + petName);
            LOGGER.warning("Unmark: pet not found: " + petName);
            return;
        }

        ArrayList<Treatment> treatments = pet.getTreatments();
        if (treatments.isEmpty()) {
            System.out.println(petName + " has no treatments to unmark.");
            LOGGER.fine("Unmark: empty treatment list for " + petName);
            return;
        }

        int idx = index1Based - 1; // convert to 0-based
        if (idx < 0 || idx >= treatments.size()) {
            System.out.println("Invalid treatment index. Use 'list treatments n/" + petName + "' to check indexes.");
            LOGGER.warning("Unmark: invalid index " + index1Based + " for " + petName);
            return;
        }

        Treatment t = treatments.get(idx);
        t.setCompleted(false);
        System.out.println("Unmarked treatment #" + index1Based + " for " + petName
                + " (now set as NOT completed): \"" + t.getName() + "\".");
        LOGGER.info("Unmarked: " + petName + " i/" + index1Based);
    }

    private void printUsage() {
        System.out.println("Usage: unmark n/PET_NAME i/INDEX");
        System.out.println("Example: unmark n/Milo i/2");
    }

    /**
     * Parses arguments of the form "n/NAME i/INDEX" (order-insensitive).
     * NAME is taken as the token immediately after 'n/' (no spaces).
     */
    private Parsed parseArgs(String args) {
        Parsed p = new Parsed();
        if (args == null) {
            p.valid = false;
            return p;
        }
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            p.valid = false;
            return p;
        }

        String[] tokens = trimmed.split("(?=[ni]/)");
        String name = null;
        Integer idx = null;
        boolean badIndexFormat = false;
        boolean sawName = false;
        boolean sawIndex = false;

        for (String token : tokens) {
            if (token.startsWith("n/")) {
                name = token.substring(2);
                sawName = true;
            } else if (token.startsWith("i/")) {
                String num = token.substring(2);
                try {
                    idx = Integer.parseInt(num);
                    sawIndex = true;
                } catch (NumberFormatException e) {
                    badIndexFormat = true;
                }
            }
        }

        p.petName = name;
        p.index = (idx == null) ? -1 : idx;
        p.valid = sawName && sawIndex && !badIndexFormat && p.index > 0 && !p.petName.isEmpty();
        return p;
    }

    private static final class Parsed {
        String petName;
        int index;
        boolean valid;
    }
}
