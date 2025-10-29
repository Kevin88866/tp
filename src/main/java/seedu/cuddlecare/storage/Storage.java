package seedu.cuddlecare.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;
import seedu.cuddlecare.Treatment;

public class Storage {

    /**
     * Logger instance for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());

    /**
     * Save file location.
     */
    private String filePath;

    /**
     * List of all pets.
     */
    private final PetList pets;

    /**
     * Initializes the storage system.
     * @param filePath the save file location
     * @param pets the list of all pets
     */
    public Storage(String filePath, PetList pets) {
        this.filePath = filePath;
        this.pets = pets;
    }

    /**
     * Saves all the pet and treatment information
     * to the save file.
     */
    public void save() {
        try {
            File file = new File(filePath).getParentFile();
            if (!file.exists()) {
                Files.createDirectories(file.toPath());
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                if (pets.size() == 0) {
                    writer.write("No pets found.\n");
                } else {
                    writer.write("CuddleCare Records\n");
                    writer.write("===================\n\n");

                    for (int i = 0; i < pets.size(); i++) {
                        Pet pet = pets.get(i);
                        writer.write((i + 1) + ". " + pet.toString() + "\n");

                        if (pet.getTreatments().isEmpty()) {
                            writer.write("\tNo treatments recorded.\n\n");
                        } else {
                            writer.write("\tTreatments:\n");
                            for (Treatment t : pet.getTreatments()) {
                                writer.write("\t- " + t.toString() + "\n");
                            }
                            writer.write("\n");
                        }
                    }
                }
            }

            LOGGER.log(Level.INFO, "Data successfully saved to " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save data: " + e.getMessage());
            System.out.println("Failed to save data.");
        }
    }
}
