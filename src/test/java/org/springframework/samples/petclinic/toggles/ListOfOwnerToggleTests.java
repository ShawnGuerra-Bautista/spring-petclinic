package org.springframework.samples.petclinic.toggles;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerController;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.system.PetClinicToggles;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ListOfOwnerToggleTests {

    @Mock
    OwnerRepository mockOwnerRepository;
    @Mock
    Logger mockOwnerListCsvLogger;
    @Mock
    Logger mockConsoleLogger;
    @Mock
    Map<String, Object> mockModel;
    @Mock
    BindingResult mockResult;

    @Mock
    Owner mockOwner;

    @Before
    public void setup() {
        when(mockOwner.getLastName()).thenReturn("");
    }

    @Test
    public void darklaunchListOfOwner() {
        PetClinicToggles.toggleListOfOwners = false;

        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger);

        // verify going to ownerList using url correctly logs
        ownerController.showOwnerList(mockModel);
        // verify going to ownerList using url correctly logs
        ownerController.processFindForm(mockOwner, mockResult, mockModel);

        //both should have triggered a log that assume user has toggle off
        verify(mockOwnerListCsvLogger, times(2)).info("0");
        verify(mockOwnerListCsvLogger, never()).info("1");


        //now switch on toggle and show that its on
        PetClinicToggles.toggleListOfOwners = true;

        ownerController.showOwnerList(mockModel);
        ownerController.processFindForm(mockOwner, mockResult, mockModel);

        // Should be unchanged, and 2 logs suggesting toggle on should occur
        verify(mockOwnerListCsvLogger, times(2)).info("1");
        verify(mockOwnerListCsvLogger, times(2)).info("0");
    }

}
