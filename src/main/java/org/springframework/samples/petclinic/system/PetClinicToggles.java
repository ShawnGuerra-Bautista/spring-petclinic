package org.springframework.samples.petclinic.system;

public interface PetClinicToggles {
    int MIN = 0;
    int MAX = 1;
    public static final Boolean toggleFindOwnerByLastName = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static final Boolean toggleListOfOwners = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
}
