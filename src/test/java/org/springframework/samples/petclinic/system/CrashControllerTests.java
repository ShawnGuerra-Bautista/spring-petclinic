package org.springframework.samples.petclinic.system;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;



/**
 * Test class for {@link CrashController}
 *
 * @author Colin But
 */

public class CrashControllerTests {

    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testTriggerException() throws Exception {
    	CrashController crashController = new CrashController();
    	try {
    	crashController.triggerException();
    	fail("Expected runtime exception");
    	}
    	catch(RuntimeException expected) {
    	    assertEquals("Expected: controller used to showcase what "
                    + "happens when an exception is thrown", expected.getMessage());
    	  }
      
    }
    	
}
