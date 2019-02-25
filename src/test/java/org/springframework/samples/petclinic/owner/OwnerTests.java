package org.springframework.samples.petclinic.owner;

import org.junit.Test;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class OwnerTests{

    @Test
    public void testGetPets()
    {
        Set<Pet> pets = new HashSet<>();
        pets.add(new Pet(null, "Bella",LocalDate.now() , new PetType(null, "dog"), null));
        pets.add(new Pet(null, "Lucy", LocalDate.now(), new PetType(null, "cat"), null));
        pets.add(new Pet(null, "Daisy", LocalDate.now(), new PetType(null, "hamster"), null));
        pets.add(new Pet(null, "Molly", LocalDate.now(), new PetType(null, "snake"), null));
        pets.add(new Pet(null, "Charlie", LocalDate.now(), new PetType(null, "dog"), null));

        // mock propertycomparator class
        PropertyComparator propertyComparator = mock(PropertyComparator.class);

        //create owner object with mocks
        Owner owner = new Owner(pets, propertyComparator);

        //create a list of pets that are sorted
        List<Pet> findPets = owner.getPets();

        //verify that method was called 
        verify(propertyComparator).sort(new ArrayList<>(pets), new MutableSortDefinition("name", true, true));


    }


}