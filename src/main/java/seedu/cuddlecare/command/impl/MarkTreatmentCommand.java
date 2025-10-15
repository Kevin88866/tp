package seedu.cuddlecare.command.impl;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

/**
 * Marks a treatment as completed for a specific pet by local index.
 * <p>Usage: {@code mark n/PET_NAME i/INDEX}</p>
 * Example: {@code mark n/Milo i/2}
 */
public class MarkTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(MarkTreatmentCommand.class.getName());

    static {
        LOGGER.setLevel(Level.OFF);
    }

    /** Repository of pets. */
    private final PetList pets;

    /**
     * @param pets repository of pets
     */
    public MarkTreatmentCommand(PetList pets) {
        this.pets = pets;
    }

    /**
     * Executes the mark operation.
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
            System.out.println("No such pet: " + petName);
            LOGGER.warning("Mark: pet not found: " + petName);
            return;
        }

        ArrayList<Treatment> treatments = pet.getTreatments();
        if (treatments.isEmpty()) {
            System.out.println(petName + " has no treatments to mark.");
            LOGGER.fine("Mark: empty treatment list for " + petName);
            return;
        }

        int idx = index1Based - 1; // convert to 0-based
        if (idx < 0 || idx >= treatments.size()) {
            System.out.println("No such treatment");
            System.out.println("Pet: " + petName);
            System.out.println("Index: " + index1Based);
            LOGGER.warning(() -> "Mark: invalid index " + index1Based + " for " + petName
                    + " (size=" + treatments.size() + ")");
            return;
        }

        Treatment t = treatments.get(idx);
        t.setCompleted(true);
        System.out.println("Marked as done");
        System.out.println("Pet: " + petName);
        System.out.println("Index: " + index1Based);
        LOGGER.info(() -> "Marked: " + petName + " i/" + index1Based + " \"" + t.getName() + "\"");
    }

    private void printUsage() {
        System.out.println("Usage: mark n/PET_NAME i/INDEX");
        System.out.println("Example: mark n/Milo i/2");
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
        boolean isBadIndex = false;
        boolean hasName = false;
        boolean hasIndex = false;

        for (String tok : tokens) {
            if (tok.startsWith("n/")) {
                name = tok.substring(2).trim();
                hasName = true;
            } else if (tok.startsWith("i/")) {
                String num = tok.substring(2).trim();
                try {
                    idx = Integer.parseInt(num);
                    hasIndex = true;
                } catch (NumberFormatException e) {
                    p.valid = false;
                    return p;
                }
            }
        }

        p.petName = name;
        p.index = (idx == null) ? -1 : idx;
        p.valid = hasName && hasIndex && p.index > 0 && !p.petName.isEmpty();
        return p;
    }

    private static final class Parsed {
        String petName;
        int index;
        boolean valid;
    }
}
