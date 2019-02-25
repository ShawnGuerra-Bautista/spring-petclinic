package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.validation.Errors;

public class PetValidatorTest {
	
     /**
      * validates when pets birthday is null
     */
	  @Test
	    public void ValidatWhenBirthdayDateIsNull() {
	    	PetValidator petValidate=new PetValidator();
	    	Pet pet=mock(Pet.class);
	    	PetType petType=mock(PetType.class);
	    	Errors error=mock(Errors.class);
	    	when(pet.getName()).thenReturn("Garfield");
	    	when(pet.getId()).thenReturn(1);
	    	when(pet.getType()).thenReturn(petType);
	    	when(pet.getBirthDate()).thenReturn(null);
	    	petValidate.validate(pet, error);
	    	verify(error).rejectValue("birthDate","required","required");
	    }
	  
	  /**
	   * validates when pets name is undefined (not set)
	   */
	  
	  @Test
	  public void ValidateWhenNameIsUndefined() {
		  PetValidator petValidate=new PetValidator();
	      Pet pet=mock(Pet.class);
	      Errors error=mock(Errors.class);
	      petValidate.validate(pet, error);
	      verify(pet).getName();
	      verify(error).rejectValue("name","required","required");   
	      
	  }

}
