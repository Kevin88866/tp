package seedu.cuddlecare.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            Map<String, Pet> petMap = new HashMap<>();
            Map<String, ArrayList<Treatment>> treatmentsMap = new HashMap<>();
            boolean isReadingPets = true;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.equalsIgnoreCase("# Pets")) {
                    isReadingPets = true;
                    continue;
                }

                if (line.equalsIgnoreCase("# Treatments")) {
                    isReadingPets = false;
                    continue;
                }

                if (isReadingPets) {
                    String[] parts = line.split("\\|");
                    processPet(parts, petMap);
                } else {
                    String[] parts = line.split("\\|", 5);
                    processTreatment(parts, treatmentsMap);
                }
            }

            loadTreatments(petMap, treatmentsMap);

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
            LOGGER.log(Level.WARNING, "Unrecognized or malformed " +
                    "line: " + String.join(" | ", parts));
            return;
        }

        String name = parts[0].toLowerCase().trim();
        String species = parts[1].toLowerCase().trim();
        int age;

        if (name.isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid pet name -> blank");
            return;
        }

        if (species.isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid pet species -> blank");
            return;
        }

        try {
            age = Math.abs(Integer.parseInt(parts[2].trim()));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid age for pet " + name);
            return;
        }

        if (petMap.containsKey(name)) {
            LOGGER.log(Level.WARNING, "Pet already added: "+name);
            return;
        }

        Pet pet = new Pet(name, species, age);
        pets.add(pet);
        petMap.put(name, pet);
    }

    /**
     * Parses a line from the save file representing a treatment and stores it
     * in a temporary map until all pets have been processed.
     *
     * @param parts          the split components of the line
     *                       (format: petName | treatmentName | date | completed | note)
     * @param treatmentsMap  a map linking lowercase pet names to their list of {@link Treatment} objects
     */
    private void processTreatment(String[] parts,
                                  Map<String, ArrayList<Treatment>> treatmentsMap) {
        if (parts.length < 4) {
            LOGGER.log(Level.WARNING, "Malformed Treatment " +
                    "-> Ignoring: "+String.join(" | ", parts));
            return;
        }

        String petName = parts[0].toLowerCase().trim();
        String treatmentName = parts[1].toLowerCase().trim();
        LocalDate date;

        if (petName.isEmpty()) {
            LOGGER.log(Level.WARNING, "PetName cannot be empty for the provided treatment");
            return;
        }

        if (treatmentName.isEmpty()) {
            LOGGER.log(Level.WARNING, "Treatment name cannot be empty");
            return;
        }

        try {
            date = LocalDate.parse(parts[2].trim());
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.WARNING, "Invalid date for treatment " + treatmentName);
            return;
        }

        boolean isComplete = Boolean.parseBoolean(parts[3].trim());
        String note = (parts.length >= 5) ? parts[4].trim() : "";

        Treatment t = new Treatment(treatmentName, note, date);
        t.setCompleted(isComplete);

        if (treatmentsMap.containsKey(petName)) {
            treatmentsMap.get(petName).add(t);
        } else {
            ArrayList<Treatment> newList = new ArrayList<>();
            newList.add(t);
            treatmentsMap.put(petName, newList);
        }
    }

    /**
     * Assigns treatments from the temporary map to their corresponding pets.
     * Logs a warning for treatments whose referenced pet does not exist
     * or if the treatment is a duplicate.
     *
     * @param petMap        map of lowercase pet names to their corresponding {@link Pet} objects
     * @param treatmentsMap map of lowercase pet names to their list of {@link Treatment} objects
     */
    private void loadTreatments(Map<String, Pet> petMap, Map<String,
            ArrayList<Treatment>> treatmentsMap) {

        for (Map.Entry<String, Pet> entry: petMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        for (Map.Entry<String, ArrayList<Treatment>> entry: treatmentsMap.entrySet()) {
            Pet pet = petMap.get(entry.getKey());
            if (pet == null) {
                String treatmentNames = entry.getValue().stream()
                        .map(Treatment::getName)
                        .collect(Collectors.joining(", "));
                LOGGER.log(Level.WARNING, String.format(
                        "Pet '%s' not found for treatments: %s",
                        entry.getKey(), treatmentNames));
                continue;
            }

            for (Treatment treatment: entry.getValue()) {
                if (pet.isDuplicateTreatment(treatment)) {
                    LOGGER.log(Level.WARNING, String.format("Duplicate Treatment '%s' for pet '%s'", treatment, pet));
                    continue;
                }
                pet.addTreatment(treatment);
            }
        }
    }

    /**
     * Saves all the pet and treatment information
     * to the save file.
     */
    public void save() {
        try {
            createSaveDirectory();
            FileWriter writer = new FileWriter(filePath, StandardCharsets.UTF_8);
            
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
