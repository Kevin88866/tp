package seedu.cuddlecare;

import java.time.LocalDate;

/**
 * Represents a treatment record for a pet.
 */
public class Treatment {
    private final String name;
    private final LocalDate date;
    private boolean completed = false;
    /**
     * Creates a Treatment with a treatment name, date.
     *
     * @param name name of the treatment
     * @param date date of the treatment
     */
    public Treatment(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        String status = completed ? "[X] " : "[ ] ";
        return status + name + " on " + date;
    }

    /** Marks this treatment's completion state. */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /** Returns true if this treatment is completed. */
    public boolean isCompleted() {
        return completed;
    }


}
