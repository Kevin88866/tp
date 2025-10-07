package seedu.cuddlecare.command.impl;

import java.util.List;

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
        Parsed parsed = parseArgs(args);
        if (!parsed.valid) {
            printUsage();
            return;
        }

        Pet pet = pets.getPetByName(parsed.petName);
        if (pet == null) {
            System.out.println("No such pet: " + parsed.petName);
            return;
        }

        List<Treatment> list = pet.getTreatments();
        if (parsed.index <= 0 || parsed.index > list.size()) {
            System.out.println("No such treatment for pet " + pet.getName() + ": " + parsed.index);
            return;
        }

        Treatment t = list.get(parsed.index - 1);
        t.setCompleted(true);
        System.out.println("Marked as done: [" + t + "] (Pet: " + pet.getName() + ", i/" + parsed.index + ")");
    }

    private void printUsage() {
        System.out.println("Usage: mark n/PET_NAME i/INDEX");
    }

    private Parsed parseArgs(String args) {
        Parsed p = new Parsed();
        if (args == null) {
            return p;
        }
        String[] tokens = args.trim().split("(?=[ni]/)");
        boolean sawName = false;
        boolean sawIndex = false;
        boolean badIndexFormat = false;
        int idx = -1;

        for (String tok : tokens) {
            if (tok.startsWith("n/")) {
                p.petName = tok.substring(2).trim();
                sawName = !p.petName.isEmpty();
            } else if (tok.startsWith("i/")) {
                sawIndex = true;
                String num = tok.substring(2).trim();
                try {
                    idx = Integer.parseInt(num);
                } catch (NumberFormatException e) {
                    badIndexFormat = true;
                }
            }
        }
        p.index = idx;
        p.valid = sawName && sawIndex && !badIndexFormat && idx > 0;
        return p;
    }

    private static final class Parsed {
        String petName;
        int index;
        boolean valid;
    }
}
