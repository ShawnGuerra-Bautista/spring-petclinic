package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

public class PetValidatorTest {

	  @Test
	    public void ValidatWhenBirthdayDateIsNull() {
	    	//write a test when birthday is null
	    	PetValidator petValidate=new PetValidator();
	    	Pet pet=Mockito.mock(Pet.class);
	    	PetType petType=Mockito.mock(PetType.class);
	    	Errors error=Mockito.mock(Errors.class);
	    	Mockito.when(pet.getName()).thenReturn("Garfield");
	    	Mockito.when(pet.getId()).thenReturn(1);
	    	Mockito.when(pet.getType()).thenReturn(petType);
	    	Mockito.when(pet.getBirthDate()).thenReturn(null);
	    	petValidate.validate(pet, error);
	    	Mockito.verify(error).rejectValue("birthDate","required","required");
	    }

}
