package org.springframework.samples.petclinic.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public interface PetClinicToggles {
    int MIN = 0;
    int MAX = 1;
    public static Boolean toggleFindOwnerByLastName = true;
    public static Boolean toggleListOfOwners = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static Boolean toggleFindOwnerByFirstName = true;
    public static Collection<Boolean> toggles = new ArrayList<>(Arrays.asList(toggleFindOwnerByLastName, toggleListOfOwners, toggleFindOwnerByFirstName));
}
