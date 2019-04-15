package org.springframework.samples.petclinic.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PetClinicToggles {
    public static int MIN = 0;
    public static int MAX = 1;
    public static Boolean toggleFindOwnerByLastName = true;
    public static Boolean toggleListOfOwners = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static Collection<Boolean> toggles = new ArrayList<>(Arrays.asList(toggleFindOwnerByLastName, toggleListOfOwners));
}
