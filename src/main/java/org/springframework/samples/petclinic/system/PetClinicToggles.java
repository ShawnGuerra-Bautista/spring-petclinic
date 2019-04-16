package org.springframework.samples.petclinic.system;

import org.springframework.samples.petclinic.toggles.Toggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PetClinicToggles {
    // TODO remove min and max
    public static int MIN = 0;
    public static int MAX = 1;

    public static Toggle toggleFindOwnerByLastName = new Toggle(Toggle.ALWAYS_ON_WHEN_ENABLED);
    public static Toggle toggleListOfOwners = new Toggle(0.5f);
    public static Toggle toggleFindOwnerByLocation = new Toggle(0.65f);

    // TODO remove
    public static Collection<Boolean> toggles = new ArrayList<>(Arrays.asList(toggleFindOwnerByLastName.isOn(), toggleListOfOwners.isOn()));


    public static Collection<Boolean> getToggleValues() {
        return new ArrayList<>(Arrays.asList(
            toggleFindOwnerByLastName.isOn(),
            toggleFindOwnerByLocation.isOn(),
            toggleListOfOwners.isOn()));
    }
}
