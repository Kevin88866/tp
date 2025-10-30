package seedu.cuddlecare.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * Loads the pets and treatments from the save file.
     */
    public void load() {
        if (!Files.exists(Paths.get(filePath))) {
            LOGGER.log(Level.INFO, "Save file does not exist.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            Map<String, Pet> petMap = new HashMap<>();
            boolean isReadingPets = true;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.equalsIgnoreCase("# Pets")) {
                    continue;
                }

                if (line.equalsIgnoreCase("# Treatments")) {
                    isReadingPets = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (isReadingPets) {
                    processPet(parts, petMap);
                } else {
                    processTreatment(parts, petMap);
                }
            }

            LOGGER.log(Level.INFO, "Data successfully loaded from " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load data: " + e.getMessage());
            System.out.println("Failed to load data.");
        }
    }

    /**
     * Processes each line from the save file that contains
     * information about the pet including its name, species,
     * and age and adds it to the pet list.
     * @param parts each part of the line in the save file
     * @param petMap the name of the pet to its Pet object
     */
    private void processPet(String[] parts, Map<String, Pet> petMap) {
        if (parts.length < 3) {
            return;
        }

        String name = parts[0].trim();
        String species = parts[1].trim();
        int age;

        try {
            age = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid age for pet " + name);
            return;
        }

        Pet pet = new Pet(name, species, age);
        pets.add(pet);
        petMap.put(name, pet);
    }

    /**
     * Processes each line from the save file that contains
     * information about each treatment and assigns it to
     * the corresponding pet.
     * @param parts each part of the line in the save file
     * @param petMap the name of the pet to its Pet object
     */
    private void processTreatment(String[] parts, Map<String, Pet> petMap) {
        if (parts.length < 4) {
            return;
        }

        String petName = parts[0].trim();
        String treatmentName = parts[1].trim();
        LocalDate date;

        try {
            date = LocalDate.parse(parts[2].trim());
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.WARNING, "Invalid date for treatment " + treatmentName);
            return;
        }

        boolean isComplete = Boolean.parseBoolean(parts[3].trim());
        String note = (parts.length >= 5) ? parts[4].trim() : "";

        Pet pet = petMap.get(petName);
        if (pet == null) {
            LOGGER.log(Level.WARNING, "Pet not found for treatment " + treatmentName);
            return;
        }

        Treatment t = new Treatment(treatmentName, note, date);
        t.setCompleted(isComplete);
        pet.addTreatment(t);
    }

    /**
     * Saves all the pet and treatment information
     * to the save file.
     */
    public void save() {
        try {
            createSaveDirectory();
            FileWriter writer = new FileWriter(filePath);
            
            savePets(writer);
            saveTreatments(writer);

            writer.close();
            LOGGER.log(Level.INFO, "Data successfully saved to " + filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save data: " + e.getMessage());
            System.out.println("Failed to save data.");
        }
    }

    /**
     * Saves all the current treatments for each pet into
     * the save file.
     * @param writer the file writer
     * @throws IOException
     */
    private void saveTreatments(FileWriter writer) throws IOException {
        writer.write("\n# Treatments\n");
        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            for (Treatment t : pet.getTreatments()) {
                writer.write(pet.getName() + " | " + t.getName() + " | " +
                        t.getDate() + " | " + t.isCompleted() + " | " +
                        (t.hasNote() ? t.getNote() : "") + "\n");
            }
        }
    }

    /**
     * Saves all the current pets in the pet list
     * into the save file.
     * @param writer the file writer
     * @throws IOException
     */
    private void savePets(FileWriter writer) throws IOException {
        writer.write("# Pets\n");
        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            writer.write(pet.getName() + " | " + pet.getSpecies() + " | " + pet.getAge() + "\n");
        }
    }

    /**
     * Checks if the save file exists and creates
     * it otherwise.
     * @throws IOException
     */
    private void createSaveDirectory() throws IOException {
        File file = new File(filePath).getParentFile();
        if (!file.exists()) {
            Files.createDirectories(file.toPath());
        }
    }
}
