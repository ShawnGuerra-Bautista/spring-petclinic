package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.springframework.samples.petclinic.model.NamedEntity;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class OwnerTests {

    @Test
    public void testGetPets() {
        Set<Pet> pets = new HashSet<>();
        pets.add(new Pet(null, "Bella", LocalDate.now(), new PetType("dog"), null));
        pets.add(new Pet(null, "Lucy", LocalDate.now(), new PetType("cat"), null));
        pets.add(new Pet(null, "Daisy", LocalDate.now(), new PetType("hamster"), null));
        pets.add(new Pet(null, "Molly", LocalDate.now(), new PetType("snake"), null));
        pets.add(new Pet(null, "Charlie", LocalDate.now(), new PetType("dog"), null));

        //create owner object 
        Owner owner = new Owner(pets);

        //create a list of pets that are sorted
        List<Pet> sortedPets = owner.getPets();

        //assert pets are sorted
        assertEquals("Bella", sortedPets.get(0).getName());
        assertEquals("Charlie", sortedPets.get(1).getName());
        assertEquals("Daisy", sortedPets.get(2).getName());
        assertEquals("Lucy", sortedPets.get(3).getName());
        assertEquals("Molly", sortedPets.get(4).getName());


    }

    @Test
    public void testGetPetFailsIfNotOwnedbyOwner() {

        Set<Pet> pets = new HashSet<>();
        pets.add(new Pet(null, "Bella", LocalDate.now(), new PetType("dog"), null));
        pets.add(new Pet(null, "Lucy", LocalDate.now(), new PetType("cat"), null));

        Owner owner = new Owner(pets, (Comparator<NamedEntity>) mock(Comparator.class));

        Pet petShouldNotExist = owner.getPet("No pet has this name", false);
        assertNull(petShouldNotExist);
    }

    @Test
    public void testGetPetPassIfNewPetIsAdded() {

        Set<Pet> pets = new HashSet<>();
        Pet pet = new Pet(5, "Buster", LocalDate.now(), new PetType("dog"), null);
        pets.add(pet);
        Owner owner = new Owner(pets, (Comparator<NamedEntity>) mock(Comparator.class));

        Pet newPetIsAdded = owner.getPet("Buster", false);
        assertEquals(pet, newPetIsAdded);

    }

    @Test
    public void testGetPetPassIfTypeDoesNotExis() {
        Set<Pet> pets = new HashSet<>();
        Pet pet = new Pet(5, "Buster", LocalDate.now(), null, null);
        pets.add(pet);
        Owner owner = new Owner(pets, (Comparator<NamedEntity>) mock(Comparator.class));

        Pet petPassIfNoType = owner.getPet("Buster", false);
        assertEquals(pet, petPassIfNoType);
    }

    @Test
    public void testIgnoringNewPetWorks() {

        Set<Pet> pets = new HashSet<>();
        Pet petWithNoId = new Pet(null, "Buster", LocalDate.now(), new PetType("dog"), null);
        pets.add(petWithNoId);

        Owner owner = new Owner(pets, (Comparator<NamedEntity>) mock(Comparator.class));

        Pet petGottenWithNoId = owner.getPet("Buster", false);
        assertEquals(petWithNoId, petGottenWithNoId);

        Pet ignoredPet = owner.getPet("Buster", true);
        assertNull(ignoredPet);
    }

    @Test
    public void testNameCasingDoesNotEffectGettingPet() {

        Set<Pet> pets = new HashSet<>();
        Pet pet = new Pet(5, "Buster", LocalDate.now(), new PetType("dog"), null);
        pets.add(pet);

        Owner owner = new Owner(pets, (Comparator<NamedEntity>) mock(Comparator.class));

        Pet gottenPet = owner.getPet("buStEr", false);
        assertEquals(pet, gottenPet);
    }

}
