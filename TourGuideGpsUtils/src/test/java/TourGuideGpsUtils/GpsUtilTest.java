/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package TourGuideGpsUtils;

import org.junit.Test;

import gpsUtil.location.VisitedLocation;
import tourGuideGpsUtils.GpsUtilController;
import tourGuideGpsUtils.GpsUtilService;

import static org.junit.Assert.*;

import java.util.UUID;

public class GpsUtilTest {
	
    @Test 
    public void getAttractionsTest_shouldReturnAllRegisteredAttraction() {
    	GpsUtilService service = new GpsUtilService();
        GpsUtilController controller = new GpsUtilController(service);
        
        
        assertEquals(26, controller.getAttractions().size());
        
    }
    
    @Test
    public void getUserLocationTest_shouldReturnUserLocation() {
      	GpsUtilService service = new GpsUtilService();
        GpsUtilController controller = new GpsUtilController(service);
        
        VisitedLocation visitedLocation = controller.getUserLocation(UUID.randomUUID());
        
        assertNotNull(visitedLocation.location);
    }
}
