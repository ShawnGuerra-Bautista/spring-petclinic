package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;

public class PetValidatorTest {
	
     /**
      * validates when pets birthday is null
     */
	  @Test
	    public void ValidatWhenBirthdayDateIsNull() {
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
	  
	  /**
	   * validates when pets name is undefined (not set)
	   */
	  
	  @Test
	  public void ValidateWhenNameIsUndefined() {
		  PetValidator petValidate=new PetValidator();
	      Pet pet=Mockito.mock(Pet.class);
	      Errors error=Mockito.mock(Errors.class);
	      petValidate.validate(pet, error);
	      Mockito.verify(pet).getName();
	      Mockito.verify(error).rejectValue("name","required","required");   
	      
	  }

}
