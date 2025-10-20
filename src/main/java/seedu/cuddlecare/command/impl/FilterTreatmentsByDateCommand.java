package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;
import seedu.cuddlecare.command.Command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Filters treatments within a date range, optionally for a single pet.
 * <p>Usage:</p>
 * <ul>
 *   <li>{@code filter-treatments from/YYYY-MM-DD to/YYYY-MM-DD}</li>
 *   <li>{@code filter-treatments n/PET_NAME from/YYYY-MM-DD to/YYYY-MM-DD}</li>
 * </ul>
 * Dates are inclusive.
 */
public class FilterTreatmentsByDateCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(FilterTreatmentsByDateCommand.class.getName());
    private final PetList pets;

    /**
     * @param pets repository of pets
     */
    public FilterTreatmentsByDateCommand(PetList pets) {
        this.pets = pets;
    }

    @Override
    public void exec(String args) {
        assert pets != null : "Pet list must not be null";

        try {
            Parsed p = parseArgs(args);
            if (!p.valid) {
                printUsage();
                LOGGER.log(Level.WARNING, "Invalid args for filter-treatments: \"{0}\"", args);
                return;
            }

            List<ResultRow> results = new ArrayList<>();

            if (p.petName != null && !p.petName.isEmpty()) {
                Pet pet = pets.getPetByName(p.petName);
                if (pet == null) {
                    System.out.println("No such pet: " + p.petName);
                    LOGGER.log(Level.INFO, "Filter failed; unknown pet \"{0}\"", p.petName);
                    return;
                }
                for (Treatment t : pet.getTreatments()) {
                    if (!t.getDate().isBefore(p.from) && !t.getDate().isAfter(p.to)) {
                        results.add(new ResultRow(pet, t));
                    }
                }
            } else {
                for (int i = 0; i < pets.size(); i++) {
                    Pet pet = pets.get(i);
                    for (Treatment t : pet.getTreatments()) {
                        if (!t.getDate().isBefore(p.from) && !t.getDate().isAfter(p.to)) {
                            results.add(new ResultRow(pet, t));
                        }
                    }
                }
            }

            if (results.isEmpty()) {
                String scope = (p.petName == null) ? "all pets" : p.petName;
                System.out.println("No treatments found for " + scope + " between "
                        + p.from + " and " + p.to + ".");
                LOGGER.fine("FilterTreatmentsByDateCommand: no matches");
                return;
            }

            results.sort(Comparator.comparing(r -> r.treatment.getDate()));

            if (p.petName == null) {
                System.out.println("Treatments between " + p.from + " and " + p.to + ":");
            } else {
                System.out.println(p.petName + "'s treatments between " + p.from + " and " + p.to + ":");
            }

            int idx = 1;
            for (ResultRow r : results) {
                System.out.println((idx++) + ". " + r.pet.getName() + ": " + r.treatment);
            }

            LOGGER.log(Level.INFO, "Filtered {0} treatment(s) from {1} to {2}{3}",
                    new Object[]{results.size(), p.from, p.to, p.petName == null ? "" : (" for " + p.petName)});

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
            LOGGER.log(Level.WARNING, "Invalid date in filter-treatments", e);
        } catch (Exception e) {
            System.out.println("Unable to filter treatments. Please try again.");
            LOGGER.log(Level.WARNING, "Unexpected error in filter-treatments", e);
        }
    }

    private void printUsage() {
        System.out.println("Usage: filter-treatments [n/PET_NAME] from/YYYY-MM-DD to/YYYY-MM-DD");
    }

    private Parsed parseArgs(String args) {
        Parsed p = new Parsed();
        if (args == null) {
            p.valid = false;
            return p;
        }

        String name = null;
        String fromStr = null;
        String toStr = null;
        String[] tokens = args.trim().split("(?=n/|from/|to/)");
        for (String t : tokens) {
            if (t.startsWith("n/")) {
                name = t.substring(2).trim();
            } else if (t.startsWith("from/")) {
                fromStr = t.substring(5).trim();
            } else if (t.startsWith("to/")) {
                toStr = t.substring(3).trim();
            }
        }

        if (fromStr == null || toStr == null) {
            p.valid = false;
            return p;
        }

        LocalDate from = LocalDate.parse(fromStr);
        LocalDate to = LocalDate.parse(toStr);
        if (to.isBefore(from)) {
            LocalDate tmp = from;
            from = to;
            to = tmp;
        }

        p.petName = name;
        p.from = from;
        p.to = to;
        p.valid = true;
        return p;
    }

    private static final class Parsed {
        String petName;
        LocalDate from;
        LocalDate to;
        boolean valid;
    }

    private static final class ResultRow {
        final Pet pet;
        final Treatment treatment;
        ResultRow(Pet p, Treatment t) {
            this.pet = p;
            this.treatment = t;
        }
    }
}
