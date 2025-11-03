package seedu.cuddlecare.parser;

import seedu.cuddlecare.parser.args.EditPetArgs;

import java.util.regex.Pattern;

/** Parser for edit-pet. */
public final class EditPetParser {
    private EditPetParser() {}

    /**
     * Recognized prefixes:
     *   n/  -> old name (required)
     *   nn/ -> new name (optional)
     *   s/  -> species (optional)
     *   a/  -> age (optional; integer > 0)
     * Valid if n/ exists and at least one of nn/, s/, a/ is present.
     */
    public static EditPetArgs parse(String args) {
        EditPetArgs p = new EditPetArgs();
        if (args == null) {
            p.valid = false;
            return p;
        }
        String input = args.trim();
        if (input.isEmpty()) {
            p.valid = false;
            return p;
        }

        String oldName = null;
        String newName = null;
        String species = null;
        Integer age = null;

        boolean sawEditField = false;

        String[] parts = input.split("\\s+(?=n/|nn/|s/|a/)");
        for (String part : parts) {
            String tok = part.trim();
            if (tok.isEmpty()) {
                continue;
            }

            if (tok.startsWith("n/")) {
                oldName = tok.substring(2).trim();
            } else if (tok.startsWith("nn/")) {
                newName = tok.substring(3).trim();
                sawEditField = true;
            } else if (tok.startsWith("s/")) {
                species = tok.substring(2).trim();
                sawEditField = true;
            } else if (tok.startsWith("a/")) {
                String v = tok.substring(2).trim();
                try {
                    age = Integer.parseInt(v);
                    sawEditField = true;
                } catch (NumberFormatException e) {
                    p.valid = false;
                    return p;
                }
            }
        }

        if (oldName == null) {
            p.valid = false;
            return p;
        }
        if (!sawEditField) {
            p.valid = false;
            return p;
        }

        Pattern valid = Pattern.compile("[a-zA-Z\\- ]+");

        if (!valid.matcher(oldName).matches()) {
            p.valid = false;
            return p;
        }

        if (newName != null) {
            if (!valid.matcher(newName).matches() || newName.length() > 20) {
                p.valid = false;
                return p;
            }
        }

        if (species != null) {
            if (!valid.matcher(species).matches() || species.length() > 30) {
                p.valid = false;
                return p;
            }
        }

        if (age != null) {
            if (age < 0 || age > 200) {
                p.valid = false;
                return p;
            }
        }

        p.oldName = oldName;
        p.newName = newName;
        p.species = species;
        p.age = age;
        p.valid = true;
        return p;
    }
}
