package seedu.cuddlecare;

import java.time.LocalDate;

/**
 * Represents a treatment record for a pet.
 */
public class Treatment {
    private final String name;
    private final LocalDate date;
    private final String type;

    /**
     * Creates a Treatment with a name, date, and type.
     *
     * @param name name of the treatment
     * @param date date of the treatment
     * @param type type/category of the treatment
     */
    public Treatment(String name, LocalDate date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) on %s", name, type, date);
    }
}
