package seedu.cuddlecare;

import java.time.LocalDate;

/**
 * Represents a treatment record for a pet.
 */
public class Treatment {
    private final String name;
    private final LocalDate date;

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
        return name + " on " + date;
    }
}
