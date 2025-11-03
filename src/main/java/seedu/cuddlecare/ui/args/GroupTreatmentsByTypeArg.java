package seedu.cuddlecare.ui.args;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.Treatment;

public class GroupTreatmentsByTypeArg {
    public static class Row {
        public final Pet pet;
        public final Treatment t;
        public Row(Pet pet, Treatment t) {
            this.pet = pet;
            this.t = t;
        }
    }
}
