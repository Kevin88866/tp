package seedu.cuddlecare.parser;

import seedu.cuddlecare.parser.args.GroupTreatmentsByTypeArgs;

/** Parser for group-treatments-by-type. */
public final class GroupTreatmentsByTypeParser {
    private GroupTreatmentsByTypeParser() {}

    /**
     * Accepts optional n/PET_NAME. Any other content yields usage error.
     */
    public static GroupTreatmentsByTypeArgs parse(String args) {
        GroupTreatmentsByTypeArgs p = new GroupTreatmentsByTypeArgs();
        if (args == null || args.isBlank()) {
            return p;
        }
        String trimmed = args.trim();
        if (trimmed.startsWith("n/")) {
            p.petName = trimmed.substring(2).trim();
            return p;
        }
        p.valid = false;
        return p;
    }
}
