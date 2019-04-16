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

import static net.andreinc.mockneat.unit.networking.IPv6s.ipv6s;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;



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
    HttpServletRequest mockRequest;
    @Mock
    HttpServletRequest mockRequestForData;

    @Mock
    Owner mockOwner;

    private List<Boolean> toggles;
    private String ip = "0:0:0:0:0:0:0:1";

    @Before
    public void setup() {
        // remove any randomness by default
        PetClinicToggles.toggleListOfOwners.setRolloutRatio(Toggle.ALWAYS_ON_WHEN_ENABLED);

        // case where user enter nothing into lastName search box
        when(mockOwner.getLastName()).thenReturn("");

        when(mockRequest.getRemoteAddr()).thenReturn(ip);
    }

    @Test
    public void darklaunchListOfOwner() {
        PetClinicToggles.toggleListOfOwners.turnOff();

        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger, null);

        // verify going to ownerList using url correctly logs
        ownerController.showOwnerList(mockModel, mockRequest);
        // verify going to ownerList using url correctly logs
        ownerController.processFindForm(mockOwner, mockResult, mockModel, mockRequest);

        // get toggles in same way as front-end to verify if option shows
        toggles = new ArrayList<>(PetClinicToggles.getToggleValues());
        // should not show
        assertFalse(toggles.get(1));

        //both should have triggered a log that assume user has toggle off
        verify(mockOwnerListCsvLogger, times(2)).info(ip + ",0");
        verify(mockOwnerListCsvLogger, never()).info(ip + ",1");


        //now switch on toggle and show that its on
        PetClinicToggles.toggleListOfOwners.turnOn();

        ownerController.showOwnerList(mockModel, mockRequest);
        ownerController.processFindForm(mockOwner, mockResult, mockModel, mockRequest);

        // now user should see feature
        toggles = new ArrayList<>(PetClinicToggles.getToggleValues());
        assertTrue(toggles.get(1));

        // Should be unchanged, and 2 logs suggesting toggle on should occur
        verify(mockOwnerListCsvLogger, times(2)).info(ip + ",1");
        verify(mockOwnerListCsvLogger, times(2)).info(ip + ",0");
    }

    @Test
    public void testRandomRollout(){
        for(int i = 0; i < 50; i++){
            PetClinicToggles.toggleListOfOwners.setRolloutRatio(0.25f);
            PetClinicToggles.toggleListOfOwners.setToggleUsingRolloutRatio();

            OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger, null);
            ownerController.showOwnerList(mockModel, mockRequest);
            ownerController.processFindForm(mockOwner, mockResult, mockModel, mockRequest);
        }
        assertThat(PetClinicToggles.toggleListOfOwners.getRolloutRatio(), is(0.25f));

        for(int i = 0; i < 100; i++){
            PetClinicToggles.toggleListOfOwners.setRolloutRatio(0.50f);
            PetClinicToggles.toggleListOfOwners.setToggleUsingRolloutRatio();

            OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger, null);
            ownerController.showOwnerList(mockModel, mockRequest);
            ownerController.processFindForm(mockOwner, mockResult, mockModel, mockRequest);
        }
        assertThat(PetClinicToggles.toggleListOfOwners.getRolloutRatio(), is(0.50f));
    }

    @Test
    public void rollBackToggle() {
        PetClinicToggles.toggleListOfOwners.turnOn();

        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, mockOwnerListCsvLogger, null);

        // user uses feature which is logged
        ownerController.showOwnerList(mockModel, mockRequest);
        verify(mockOwnerListCsvLogger).info(ip + ",1");

        // should show in UI
        toggles = new ArrayList<>(PetClinicToggles.getToggleValues());
        assertTrue(toggles.get(1));


        //assume there was bug found, lets rollback
        PetClinicToggles.toggleListOfOwners.turnOff();

        // Shouldn't assume new feature is on
        ownerController.showOwnerList(mockModel, mockRequest);
        verify(mockOwnerListCsvLogger).info(ip + ",0");
        // shouldn't have happened anymore
        verify(mockOwnerListCsvLogger, times(1)).info(ip + ",1");

        // should be hidden
        toggles = new ArrayList<>(PetClinicToggles.getToggleValues());
        assertFalse(toggles.get(1));
    }
    
    @Test
    public void collectFakeData() {
        Logger realCsvLogger = LogManager.getLogger("listOfOwner");
        OwnerController ownerController = new OwnerController(mockOwnerRepository, mockConsoleLogger, realCsvLogger, null);

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

            ip = ipv6s().get();
            when(mockRequestForData.getRemoteAddr()).thenReturn(ip);

            int visits;

            // when toggle on, more visits likely
            if (PetClinicToggles.toggleListOfOwners.isOn()) {
                numToggleOnUsers++;

                visits = getWeightedRandomNumber(10, 2, 0, 15);
                for (int j = 0; j < visits; j++) {
                    numToggleOn++;
                    ownerController.showOwnerList(mockModel, mockRequestForData);
                }
            } else {
                visits = getWeightedRandomNumber(0, 2, 0, 5);
                for (int j = 0; j < visits; j++) {
                    numToggleOff++;
                    ownerController.processFindForm(mockOwner, mockResult, mockModel, mockRequestForData);
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
