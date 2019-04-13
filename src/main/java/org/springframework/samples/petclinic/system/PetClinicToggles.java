package org.springframework.samples.petclinic.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public interface PetClinicToggles {
    int MIN = 0;
    int MAX = 1;
    public static final Boolean toggleFindOwnerByLastName = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static final Boolean toggleListOfOwners = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static final Boolean toggleSearchByLocation = (int)((Math.random() * (MAX - MIN + 1)) + MIN) == 0;
    public static final Collection<Boolean> toggles = new ArrayList<Boolean>(Arrays.asList(toggleFindOwnerByLastName, toggleListOfOwners, toggleSearchByLocation));
}
