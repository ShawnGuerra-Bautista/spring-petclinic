package org.springframework.samples.petclinic.system;

import java.util.Random;

public interface PetClinicToggles {
    int MIN = 0;
    int MAX = 1;
    public static Boolean toggleFindOwnerByLastName = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static Boolean toggleListOfOwners = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
}
