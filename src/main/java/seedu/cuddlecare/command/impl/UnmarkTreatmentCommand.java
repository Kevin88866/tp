package seedu.cuddlecare.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.cuddlecare.parser.UnmarkTreatmentParser;
import seedu.cuddlecare.parser.args.UnmarkTreatmentArgs;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;
import seedu.cuddlecare.ui.Ui;

/**
 * Unmarks (sets not completed) a treatment for a specific pet by local index.
 * <p>Usage: {@code unmark n/PET_NAME i/INDEX}</p>
 * Example: {@code unmark n/Milo i/2}
 */
public class UnmarkTreatmentCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(UnmarkTreatmentCommand.class.getName());


    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "unmark n/PET_NAME i/INDEX";
    private static final String SHORT_DESCRIPTION = "Unmarks a treatment (sets it as not completed) for a pet.";
    private static final String LONG_DESCRIPTION = "Unmarks a specific treatment for a given pet by " +
            "its local index. Specify the pet using n/PET_NAME" +
            " and the treatment index using i/INDEX.";
    private static final List<String> CATEGORIES = List.of("Treatment");
    // @@author

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
        UnmarkTreatmentArgs parsed = UnmarkTreatmentParser.parse(args);

        if (!parsed.valid) {
            Ui.printUnmarkUsage();
            return;
        }

        String petName = parsed.petName;
        int index1Based = parsed.index;

        Pet pet = pets.getPetByName(petName);
        if (pet == null) {
            Ui.println("No such pet: " + petName);
            LOGGER.warning("Mark: pet not found: " + petName);
            return;
        }

        ArrayList<Treatment> treatments = pet.getTreatments();
        if (treatments.isEmpty()) {
            Ui.println(petName + " has no treatments to unmark.");
            LOGGER.fine("Unmark: empty treatment list for " + petName);
            return;
        }

        int idx = index1Based - 1;
        if (idx < 0 || idx >= treatments.size()) {
            Ui.println("No such treatment");
            Ui.println("Pet: " + petName);
            Ui.println("Index: " + index1Based);
            LOGGER.warning(() -> "Mark: invalid index " + index1Based + " for " + petName
                    + " (size=" + treatments.size() + ")");
            return;
        }

        Treatment t = treatments.get(idx);
        t.setCompleted(false);
        Ui.println("Unmarked");
        Ui.println("Pet: " + petName);
        Ui.println("Index: " + index1Based);
        LOGGER.info(() -> "Marked: " + petName + " i/" + index1Based + " \"" + t.getName() + "\"");
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

}
