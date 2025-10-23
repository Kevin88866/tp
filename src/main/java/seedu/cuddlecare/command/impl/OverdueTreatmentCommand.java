package seedu.cuddlecare.command.impl;

import seedu.cuddlecare.PetList;
import seedu.cuddlecare.command.Command;

public class OverdueTreatmentCommand implements Command {

    private final PetList pets;

    public OverdueTreatmentCommand(PetList pets) {
        this.pets = pets;
    }

    public void exec(String args) {
        
    }
}
