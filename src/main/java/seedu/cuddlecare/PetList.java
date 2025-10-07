package seedu.cuddlecare;

import java.util.ArrayList;

/**
 * A list that contains all the user's pets.
 *
 */
public class PetList {
    
    /** Underlying data structure to hold all pets */
    private final ArrayList<Pet> pets;

    /**
     * Initializes the list of pets.
     */
    public PetList() {
        this.pets = new ArrayList<>();
    }

    /**
     * Adds the unique pet to the user's pet list.
     * 
     * @param pet the newly to-be-added pet
     * @return success
     */
    public boolean add(Pet pet) {
        if (isDuplicateName(pet.getName())) {
            return false;
        }
        pets.add(pet);
        return true;
    }

    /**
     * Checks if pet already exists within the pet list.
     * 
     * @param name the name of the pet
     * @return boolean whether pet exists or not
     */
    private boolean isDuplicateName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a pet by its name in the list.
     *
     * @param name the index number of pet
     * @return pet if found, null if index is invalid
     */
    public Pet getPetByName(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return pet;
            }
        }
        return null;
    }
}
