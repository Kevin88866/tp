package seedu.cuddlecare.parser;

import seedu.cuddlecare.parser.args.EditPetArgs;

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
        if (args == null || args.trim().isEmpty()) {
            p.valid = false;
            return p;
        }

        String[] tokens = args.split("\s+(?=nn/|n/|s/|a/)");
        String oldName = null;
        String newName = null;
        String species = null;
        Integer age = null;
        boolean sawEditField = false;

        for (String t : tokens) {
            if (t == null || t.isBlank()) {
                continue;
            }
            String tok = t.trim();
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
                if (v.isEmpty()) {
                    p.valid = false;
                    return p;
                }
                age = Integer.parseInt(v);
                sawEditField = true;
                if(age <= 0) {
                    p.valid = false;
                    return p;
                }
            }
        }

        if (oldName == null || oldName.isEmpty()) {
            p.valid = false;
            return p;
        }
        if (!sawEditField) {
            p.valid = false;
            return p;
        }

        p.oldName = oldName;
        p.newName = newName;
        p.species = species;
        p.age = age;
        p.valid = true;
        return p;
    }
}
