package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.visit.Visit;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class VisitComparatorTests {
	
	 private VisitComparator visitComparator;

	    @Before
	    public void setup() {
	        this.visitComparator = new VisitComparator();
	    }
	    
	    @Test
	    public void testDatesAreInAscendingOrder() {
	        List<Visit> visits = new ArrayList<>();
	        visits.add(new Visit());
	        visits.add(new Visit());
	        visits.add(new Visit());
	        visits.add(new Visit());
	        
	        visits.get(0).setDate(LocalDate.of(2019, 04, 30));
	        visits.get(1).setDate(LocalDate.of(2019, 01, 01));
	        visits.get(2).setDate(LocalDate.of(2019, 03, 02));
	        visits.get(3).setDate(LocalDate.of(2019, 02, 12));
	        
	        visits.sort(visitComparator);

	        assertEquals(LocalDate.of(2019, 01, 01), visits.get(0).getDate());
	        assertEquals(LocalDate.of(2019, 02, 12), visits.get(1).getDate());
	        assertEquals(LocalDate.of(2019, 03, 02), visits.get(2).getDate());
	        assertEquals(LocalDate.of(2019, 04, 30), visits.get(3).getDate());
	    }
	

}
