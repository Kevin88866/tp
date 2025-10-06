package seedu.cuddlecare;

import java.util.ArrayList;
import java.util.List;

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

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public List<Treatment> getTreatments() {
        return treatments;
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
