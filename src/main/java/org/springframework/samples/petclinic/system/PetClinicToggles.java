package org.springframework.samples.petclinic.system;

import org.springframework.samples.petclinic.toggles.Toggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PetClinicToggles {

    // Our toggles
    public static Toggle toggleFindOwnerByLastName = new Toggle(Toggle.ALWAYS_ON_WHEN_ENABLED);
    public static Toggle toggleListOfOwners = new Toggle(0.5f);
    public static Toggle toggleFindOwnerByFirstName = new Toggle(0.5f);
    public static Toggle toggleFindOwnerByLocation = new Toggle(0.65f);
    public static Toggle toggleFindOwnerByTelephone = new Toggle(0.5f);


    public static Collection<Boolean> getToggleValues() {
        return new ArrayList<>(Arrays.asList(
            toggleFindOwnerByLastName.isOn(),
            toggleListOfOwners.isOn(),
            toggleFindOwnerByFirstName.isOn(),
            toggleFindOwnerByLocation.isOn(),
            toggleFindOwnerByTelephone.isOn()));
    }
}
