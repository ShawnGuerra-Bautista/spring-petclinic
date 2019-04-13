package org.springframework.samples.petclinic.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public interface PetClinicToggles {
    int MIN = 1;
    int MAX = 100;
    public static Boolean toggleFindOwnerByLastName = (int)((Math.random() * (MAX - MIN + 1)) + MIN) <= 20;
    public static Boolean toggleListOfOwners = (int)((Math.random() * (MAX - MIN + 1)) + MIN) > 20;
    public static Collection<Boolean> toggles = new ArrayList<Boolean>(Arrays.asList(toggleFindOwnerByLastName, toggleListOfOwners));
}
