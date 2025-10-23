package seedu.cuddlecare;

import java.time.LocalDate;

/**
 * Represents a treatment record for a pet.
 */
public class Treatment {
    private final String name;
    private final String note;
    private final LocalDate date;
    private boolean completed = false;

    /**
     * Creates a Treatment with a treatment name, date.
     *
     * @param name name of the treatment
     * @param date date of the treatment
     */
    public Treatment(String name, String note, LocalDate date) {
        this.name = name;
        this.note = note;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        String status = completed ? "[X] " : "[ ] ";
        String noteFormat = hasNote() ? ("\n      Note: " + note) : "";
        return status + name + " on " + date + noteFormat;
    }

    /**
     * Marks this treatment's completion state.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Returns true if this treatment is completed.
     */
    public boolean isCompleted() {
        return completed;
    }

    public boolean hasNote() {
        return !(note == null || note.isEmpty());
    }


}
