package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SummaryCommandTest {
    private ByteArrayOutputStream outContent;
    private PetList pets;

    @BeforeAll
    static void muteLogs() {
        LogManager.getLogManager().reset();
        Logger root = Logger.getLogger("");
        root.setLevel(Level.OFF);
    }

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        pets = new PetList();
        Pet p1 = new Pet("Snoopy", "Dog", 1);
        Pet p2 = new Pet("Mimi", "Cat", 2);

        pets.add(p1);
        pets.add(p2);

        Treatment t1 = new Treatment("Vaccination", null, LocalDate.parse("2025-11-11"));
        Treatment t2 = new Treatment("Dental Appointment", null, LocalDate.parse("2025-12-20"));
        Treatment t3 = new Treatment("Health Checkup", null, LocalDate.parse("2025-10-09"));

        t2.setCompleted(true);
        t3.setCompleted(true);

        p1.addTreatment(t1);
        p1.addTreatment(t3);
        p2.addTreatment(t2);
    }

    @Test
    void exec_validDateRange_printsCompletedTreatmentsInRange() {
        SummaryCommand command = new SummaryCommand(pets);
        command.exec("from/2025-10-01 to/2025-12-30");

        String[] lines = outContent.toString().trim().split("\\R");

        assertEquals("Snoopy: [X] Health Checkup on 2025-10-09", lines[1]);
        assertEquals("Mimi: [X] Dental Appointment on 2025-12-20", lines[2]);
    }

    @Test
    void exec_validDateRangeNoTreatments_printsMessage() {
        SummaryCommand command = new SummaryCommand(pets);
        command.exec("from/2025-01-01 to/2025-01-01");

        assertEquals("No treatments found from 2025-01-01 to 2025-01-01.", outContent.toString().trim());
    }
}
