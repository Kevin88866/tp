package seedu.cuddlecare.parser;

import seedu.cuddlecare.parser.args.UnmarkTreatmentArgs;

/** Parser for unmark-treatment. */
public final class UnmarkTreatmentParser {
    private UnmarkTreatmentParser() {}

    /**
     * Expected tokens:
     *   n/ PET_NAME
     *   i/ INDEX (positive integer)
     */
    public static UnmarkTreatmentArgs parse(String args) {
        UnmarkTreatmentArgs p = new UnmarkTreatmentArgs();
        if (args == null || args.trim().isEmpty()) {
            p.valid = false;
            return p;
        }
        String[] tokens = args.trim().split("(?=[ni]/)");
        String name = null;
        Integer idx = null;
        boolean hasN = false;
        boolean hasI = false;

        for (String t : tokens) {
            if (t == null || t.isBlank()) {
                continue;
            }
            String tok = t.trim();
            if (tok.startsWith("n/")) {
                hasN = true;
                name = tok.substring(2).trim();
            } else if (tok.startsWith("i/")) {
                hasI = true;
                String v = tok.substring(2).trim();
                try {
                    idx = Integer.valueOf(v);
                } catch (NumberFormatException e) {
                    p.valid = false;
                    return p;
                }
            }
        }
        if (!hasN || !hasI || name.isEmpty()) {
            p.valid = false;
            return p;
        }
        if (idx <= 0) {
            p.valid = false;
            return p;
        }
        p.petName = name;
        p.index = idx;
        p.valid = true;
        return p;
    }
}
