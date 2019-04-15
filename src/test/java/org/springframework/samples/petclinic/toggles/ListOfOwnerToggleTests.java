package org.springframework.samples.petclinic.toggles;

import org.apache.logging.log4j.LogManager;
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
import java.util.concurrent.ThreadLocalRandom;

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
        // remove any randomness by default
        PetClinicToggles.toggleListOfOwners.setRolloutRatio(Toggle.ALWAYS_ON_WHEN_ENABLED);
        when(mockOwner.getLastName()).thenReturn("");
    }

    @Test
    public void darklaunchListOfOwner() {
        PetClinicToggles.toggleListOfOwners.turnOff();

        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger);

        // verify going to ownerList using url correctly logs
        ownerController.showOwnerList(mockModel);
        // verify going to ownerList using url correctly logs
        ownerController.processFindForm(mockOwner, mockResult, mockModel);

        //both should have triggered a log that assume user has toggle off
        verify(mockOwnerListCsvLogger, times(2)).info("0");
        verify(mockOwnerListCsvLogger, never()).info("1");


        //now switch on toggle and show that its on
        PetClinicToggles.toggleListOfOwners.turnOn();

        ownerController.showOwnerList(mockModel);
        ownerController.processFindForm(mockOwner, mockResult, mockModel);

        // Should be unchanged, and 2 logs suggesting toggle on should occur
        verify(mockOwnerListCsvLogger, times(2)).info("1");
        verify(mockOwnerListCsvLogger, times(2)).info("0");
    }

    @Test
    public void rollBackToggle() {
        PetClinicToggles.toggleListOfOwners.turnOn();

        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger);

        // user uses feature which is logged
        ownerController.showOwnerList(mockModel);
        verify(mockOwnerListCsvLogger).info("1");

        //assume there was bug found, lets rollback
        PetClinicToggles.toggleListOfOwners.turnOff();

        // Shouldn't assume new feature is on
        ownerController.showOwnerList(mockModel);
        verify(mockOwnerListCsvLogger).info("0");
        // shouldn't have happened anymore
        verify(mockOwnerListCsvLogger, times(1)).info("1");
    }

    @Test
    public void collectFakeData() {
        Logger realCsvLogger = LogManager.getLogger("listOfOwner");
        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, realCsvLogger);

        // set up so that around half users get new feature
        PetClinicToggles.toggleListOfOwners = new Toggle(0.5f);

        int numUsers = 50;
        int numToggleOnUsers = 0;

        int numToggleOn = 0;
        int numToggleOff = 0;

        // do data gather
        for (int i = 0; i < numUsers; i++) {
            // randomly set the toggle
            PetClinicToggles.toggleListOfOwners.setToggleUsingRolloutRatio();

            int visits;

            // when toggle on, more visits likely
            if (PetClinicToggles.toggleListOfOwners.isOn()) {
                numToggleOnUsers++;

                visits = getWeightedRandomNumber(10, 2, 0, 15);
                for (int j = 0; j < visits; j++) {
                    numToggleOn++;
                    ownerController.showOwnerList(mockModel);
                }
            } else {
                visits = getWeightedRandomNumber(0, 2, 0, 5);
                for (int j = 0; j < visits; j++) {
                    numToggleOff++;
                    ownerController.processFindForm(mockOwner, mockResult, mockModel);
                }
            }
        }

        Logger logTotals = LogManager.getLogger("trace");
        logTotals.info("Num users = " + numUsers);
        logTotals.info("Num toggle on Users = " + numToggleOnUsers);
        logTotals.info("Num toggle of Users = " + (numUsers - numToggleOnUsers));

        logTotals.info("n = " + (numToggleOn + numToggleOff));
        logTotals.info("Num toggle on visits = " + numToggleOn);
        logTotals.info("Num toggle off visits = " + (numToggleOff));
    }

    private int getWeightedRandomNumber(int weightPosition, double standardDeviation, int leftBound, int rightBound) {

        int rand = (int)(ThreadLocalRandom.current().nextGaussian() * standardDeviation + weightPosition);

        if (rand < leftBound) {
            rand = leftBound;
        }

        if (rand > rightBound) {
            rand = rightBound;
        }

        return rand;
    }

}
