package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.parser.GroupTreatmentsByTypeParser;
import seedu.cuddlecare.parser.args.GroupTreatmentsByTypeArgs;
import seedu.cuddlecare.ui.args.GroupTreatmentsByTypeArg;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import seedu.cuddlecare.ui.Ui;

/**
 * Groups treatments by type (e.g., all vaccines together), optionally across all pets
 * or for a single pet.
 *
 * <p>Usage:</p>
 * <ul>
 *   <li>{@code group-treatments}</li>
 *   <li>{@code group-treatments n/PET_NAME}</li>
 * </ul>
 *
 * <p>Notes:</p>
 * <ul>
 *   <li>Type is derived from the first word of the treatment name
 *       (e.g., "Vaccine A" → "Vaccine").</li>
 *   <li>Within each type group, items are sorted by date ascending.</li>
 * </ul>
 */
public class GroupTreatmentsByTypeCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(GroupTreatmentsByTypeCommand.class.getName());


    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "group-treatments [n/PET_NAME]";
    private static final String SHORT_DESCRIPTION = "Groups treatments by type";
    private static final String LONG_DESCRIPTION = "Groups treatments by their type (first word " +
            "of the name) either for a single pet or all " +
            "pets. Items within each group are sorted by date ascending.";
    private static final List<String> CATEGORIES = List.of("Treatment");
    // @@author

    private final PetList pets;

    /**
     * @param pets repository of pets
     */
    public GroupTreatmentsByTypeCommand(PetList pets) {
        this.pets = pets;
    }

    @Override
    public void exec(String args) {
        assert pets != null : "Pet list must not be null";

        try {
            GroupTreatmentsByTypeArgs p = GroupTreatmentsByTypeParser.parse(args);

            if (p.petName != null && !p.petName.isEmpty()) {
                Pet pet = pets.getPetByName(p.petName);
                if (pet == null) {
                    Ui.println("No such pet: " + p.petName);
                    LOGGER.log(Level.INFO, "Group by type failed; unknown pet \"{0}\"", p.petName);
                    return;
                }
                groupForSinglePet(pet);
            } else {
                hasAnyTreatments();
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unexpected error in group-treatments", e);
            Ui.println("Unable to group treatments. Please try again.");
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

    private void groupForSinglePet(Pet pet) {
        List<Treatment> ts = pet.getTreatments();
        if (ts == null || ts.isEmpty()) {
            Ui.println("No treatments for " + pet.getName() + " to group.");
            LOGGER.fine("GroupTreatmentsByTypeCommand: empty for single pet");
            return;
        }

        Map<String, List<GroupTreatmentsByTypeArg.Row>> groups = buildGroups(List.of(pet));
        Ui.printGroups(groups, pet.getName() + "'s treatments grouped by type:");
        LOGGER.log(Level.INFO, "Printed {0} group(s) by type", groups.size());
    }

    private void hasAnyTreatments() {
        boolean hasAny = false;
        for (int i = 0; i < pets.size(); i++) {
            if (!pets.get(i).getTreatments().isEmpty()) {
                hasAny = true;
                break;
            }
        }
        if (!hasAny) {
            Ui.println("No treatments logged.");
            LOGGER.log(Level.WARNING, "No treatments logged.");
            return;
        }

        Map<String, List<GroupTreatmentsByTypeArg.Row>> groups = buildGroups(pets.toList());
        Ui.printGroups(groups, "Treatments grouped by type:");
        LOGGER.log(Level.INFO, "Printed {0} group(s) by type", groups.size());
    }

    private Map<String, List<GroupTreatmentsByTypeArg.Row>> buildGroups(List<Pet> scope) {
        Map<String, List<GroupTreatmentsByTypeArg.Row>> tmp = new LinkedHashMap<>();

        for (Pet pet : scope) {
            for (Treatment t : pet.getTreatments()) {
                String type = extractType(t);
                tmp.computeIfAbsent(type, k -> new ArrayList<>()).add(new GroupTreatmentsByTypeArg.Row(pet, t));
            }
        }

        List<String> sortedTypes = new ArrayList<>(tmp.keySet());
        sortedTypes.sort(String.CASE_INSENSITIVE_ORDER);

        Map<String, List<GroupTreatmentsByTypeArg.Row>> result = new LinkedHashMap<>();
        for (String type : sortedTypes) {
            List<GroupTreatmentsByTypeArg.Row> rows = tmp.get(type);
            rows.sort(Comparator.comparing(r -> extractDate(r.t)));
            result.put(type, rows);
        }
        return result;
    }


    // GroupTreatmentsByTypeCommand.java
    private static String extractType(Treatment t) {
        String name = t.getName();
        if (name == null || name.isBlank()) {
            return "Unknown";
        }
        String[] parts = name.trim().toLowerCase().split("\\s+", 2);
        return parts[0];
    }

    private static java.time.LocalDate extractDate(Treatment t) {
        return t.getDate();
    }

}
