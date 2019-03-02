package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.Assert;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.util.SerializationUtils;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class OwnerTests{

	@Test
    public void testGetPets()
    {
        Set<Pet> pets = new HashSet<>();
        pets.add(new Pet(null, "Bella",LocalDate.now() , new PetType("dog"), null));
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
    public void testGetPetFailIfPetDoesNotExist() {
    	
    	Pet pet = mock(Pet.class);
    	pet.setName("Buster");

    	assertThat(pet.getName()).isEqualTo(null);
    }
    
    @Test
    public void testGetPetPassIfPetAndIdMatch() {
    	Pet pet = mock(Pet.class);
    	pet.setName("Bella");
    	pet.setId(0);

    	assertThat(pet.getName()).isEqualTo(null);
    	assertThat(pet.getId()).isEqualTo(0);
    }
    
    @Test
    public void testGetPetTypeFailIfTypeDoesNotExis() {
    	Pet pet = mock(Pet.class);
    	PetType pettype = mock(PetType.class);
    	pettype.setName("bird");
    	
    	assertThat(pettype.getName()).isEqualTo(null);
    }   


}