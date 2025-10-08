package seedu.cuddlecare.command.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.cuddlecare.Pet;
import seedu.cuddlecare.PetList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeletePetCommandTest {

    private PetList pets;
    private DeletePetCommand deleteCommand;

    @BeforeEach
    void setUp() {
        pets = new PetList();
        pets.add(new Pet("Buddy", "Dog", 3));
        pets.add(new Pet("Milo", "Cat", 2));
        deleteCommand = new DeletePetCommand(pets);
    }

    @Test
    void exec_validIndex_deletesPet() {
        deleteCommand.exec("i/1");
        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void exec_indexOutOfBounds_printsError() {
        deleteCommand.exec("i/5");
        assertEquals(2, pets.size());
    }

    @Test
    void exec_missingIndex_printsError() {
        deleteCommand.exec("");
        assertEquals(2, pets.size());
    }

    @Test
    void exec_invalidNumberFormat_printsError() {
        deleteCommand.exec("i/abc");
        assertEquals(2, pets.size());
    }
}
