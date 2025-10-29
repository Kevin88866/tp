package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Edits a pet's basic details (name, species, age).
 * <p>Usage: {@code edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]}</p>
 * <p>Examples:</p>
 * <ul>
 *   <li>{@code edit-pet n/Milo a/4}</li>
 *   <li>{@code edit-pet n/Milo nn/Millie s/Dog}</li>
 * </ul>
 */
public class EditPetCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(EditPetCommand.class.getName());


    // @@author HarshitSrivastavaHS
    private static final String SYNTAX = "edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]";
    private static final String SHORT_DESCRIPTION = "Edits a pet's name, species, and/or age.";
    private static final String LONG_DESCRIPTION = "Updates the details of an existing pet in the list." +
            " Specify the pet to edit using n/OLD_NAME. Optionally" +
            " provide nn/NEW_NAME, s/SPECIES, and/or a/AGE to update one or more fields.";
    private static final List<String> CATEGORIES = List.of("Pet");
    // @@author

    private final PetList pets;

    /**
     * @param pets repository of pets
     */
    public EditPetCommand(PetList pets) {
        this.pets = pets;
    }

    @Override
    public void exec(String args) {
        assert pets != null : "Pet list must not be null";

        try {
            Parsed p = parseArgs(args);
            if (!p.valid) {
                printUsage();
                LOGGER.log(Level.WARNING, "Invalid args for edit-pet: \"{0}\"", args);
                return;
            }

            Pet target = pets.getPetByName(p.oldName);
            if (target == null) {
                System.out.println("No such pet: " + p.oldName);
                LOGGER.log(Level.INFO, "Edit failed; unknown pet \"{0}\"", p.oldName);
                return;
            }

            if (p.newName != null && !p.newName.equalsIgnoreCase(p.oldName)) {
                Pet conflict = pets.getPetByName(p.newName);
                if (conflict != null && conflict != target) {
                    System.out.println("A pet named \"" + p.newName + "\" already exists.");
                    LOGGER.log(Level.INFO, "Edit aborted; name conflict: {0}", p.newName);
                    return;
                }
            }

            boolean changed = false;
            StringBuilder summary = new StringBuilder("Updated ").append(target.getName()).append(": ");

            if (p.newName != null && !p.newName.trim().isEmpty()
                    && !p.newName.equalsIgnoreCase(target.getName())) {
                summary.append("name → ").append(p.newName).append(", ");
                target.setName(p.newName);
                changed = true;
            }
            if (p.species != null && !p.species.trim().isEmpty()
                    && !p.species.equalsIgnoreCase(target.getSpecies())) {
                summary.append("species → ").append(p.species).append(", ");
                target.setSpecies(p.species);
                changed = true;
            }
            if (p.age != null && p.age != target.getAge()) {
                summary.append("age → ").append(p.age).append(", ");
                target.setAge(p.age);
                changed = true;
            }

            if (!changed) {
                System.out.println("Nothing to update. Provide at least one of nn/, s/, a/ with a new value.");
                LOGGER.fine("EditPetCommand: nothing changed");
                return;
            }

            if (summary.length() >= 2 && summary.substring(summary.length() - 2).equals(", ")) {
                summary.setLength(summary.length() - 2);
            }

            LOGGER.log(Level.INFO, "Edited pet \"{0}\" -> {1}", new Object[]{p.oldName, target});
            System.out.println(summary.toString());

        } catch (NumberFormatException e) {
            System.out.println("Age must be a valid number.");
            LOGGER.log(Level.WARNING, "Invalid age in edit-pet", e);
        } catch (Exception e) {
            System.out.println("Unable to edit pet. Please try again.");
            LOGGER.log(Level.WARNING, "Unexpected error in edit-pet", e);
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

    private void printUsage() {
        System.out.println("Usage: edit-pet n/OLD_NAME [nn/NEW_NAME] [s/SPECIES] [a/AGE]");
    }

    private Parsed parseArgs(String args) {
        Parsed p = new Parsed();
        if (args == null) {
            p.valid = false;
            return p;
        }

        String oldName = null;
        String newName = null;
        String species = null;
        Integer age = null;

        String[] tokens = args.split("\\s+(?=nn/|n/|s/|a/)");
        for (String t : tokens) {
            if (t.startsWith("n/")) {
                oldName = t.substring(2).trim();
            } else if (t.startsWith("nn/")) {
                newName = t.substring(3).trim();
            } else if (t.startsWith("s/")) {
                species = t.substring(2).trim();
            } else if (t.startsWith("a/")) {
                String a = t.substring(2).trim();
                if (!a.isEmpty()) {
                    age = Integer.parseInt(a);
                }
            }
        }

        p.oldName = oldName;
        p.newName = newName;
        p.species = species;
        p.age = age;
        p.valid = oldName != null && !oldName.isEmpty() &&
                (newName != null && !newName.isEmpty()
                        || species != null && !species.isEmpty()
                        || age != null);
        return p;
    }

    private static final class Parsed {
        String oldName;
        String newName;
        String species;
        Integer age;
        boolean valid;
    }
}
