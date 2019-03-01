package org.springframework.samples.petclinic.owner;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.validation.Errors;

public class PetValidatorTest {
	
     /**
      * validates when pets birthday is null
     */
	  @Test
	    public void ValidateWhenBirthdayDateIsNull() {
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
	  
	  /**
	   * validates when type is null
	   */
	  
	  @Test
	  public void ValidateWhenTypeIsUndefined(){
		  PetValidator petValidate=new PetValidator();
	    	Pet pet=mock(Pet.class);
	    	Errors error=mock(Errors.class);
	    	when(pet.getName()).thenReturn("Chomusuke");
	    	when(pet.getId()).thenReturn(1);
	    	when(pet.getType()).thenReturn(null);
	    	when(pet.isNew()).thenReturn(true);
			when(pet.getBirthDate()).thenReturn(LocalDate.of(2016, Month.APRIL, 1));
	    	petValidate.validate(pet, error);
	    	verify(error).rejectValue("type","required","required");
	  }

}
