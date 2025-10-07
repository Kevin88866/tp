package seedu.cuddlecare.command.impl;

import java.util.List;

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
        Parsed parsed;
        parsed = parseArgs(args);
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
        t.setCompleted(false);
        System.out.println("Unmarked: [" + t + "] (Pet: " + pet.getName() + ", i/" + parsed.index + ")");
    }

    private void printUsage() {
        System.out.println("Usage: unmark n/PET_NAME i/INDEX");
    }
    
    private Parsed parseArgs(String args) {
        Parsed p = new Parsed();
        if (args == null) {
            return p;
        }
        String[] tokens = args.trim().split("\\s+");
        for (String tok : tokens) {
            if (tok.startsWith("n/")) {
                p.petName = tok.substring(2);
            } else if (tok.startsWith("i/")) {
                try {
                    p.index = Integer.parseInt(tok.substring(2));
                } catch (NumberFormatException e) {
                    p.index = -1;
                }
            }
        }
        p.valid = p.petName != null && !p.petName.isEmpty() && p.index != 0;
        return p;
    }

    private static final class Parsed {
        String petName;
        int index;
        boolean valid;
    }
}
