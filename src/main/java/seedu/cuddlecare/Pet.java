package seedu.cuddlecare;

import java.util.ArrayList;

public class Pet {

    private String name;
    private String species;
    private int age;
    private final ArrayList<Treatment> treatments;

    /**
     * Initializes a pet with a specified name, species, and age.
     *
     * @param name name of the pet
     * @param species species of the pet
     * @param age age of the pet
     */
    public Pet(String name, String species, int age) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.treatments = new ArrayList<>();
    }

    /**
     * Returns the name of the pet.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the species of the pet.
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Returns the age of the pet.
     */
    public int getAge() {
        return age;
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    /**
     * Updates the pet's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the pet's species.
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * Updates the pet's age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Checks whether a given treatment is a duplicate of any existing treatment
     * in the pet's treatment list. A treatment is considered a duplicate if
     * it has the same name (case-insensitive) and the same date as an existing treatment.
     *
     * @param newTreatment the {@link Treatment} to check for duplication
     * @return true if a duplicate treatment exists, false otherwise
     */
    public boolean isDuplicateTreatment(Treatment newTreatment) {
        for (Treatment treatment: treatments) {
            if (treatment.getName().equalsIgnoreCase(newTreatment.getName()) &&
                treatment.getDate().equals(newTreatment.getDate())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a String composition that contains the pet's
     * name, species, and age in an organized format.
     */
    @Override
    public String toString() {
        return name + " (Species: " + species + ", Age: " + age + " years old)";
    }
}
