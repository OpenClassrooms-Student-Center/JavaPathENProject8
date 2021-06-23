package unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gps.controller.GpsController;
import gps.service.GpsServiceInterface;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class GpsControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private GpsController gpsController;

    private GpsServiceInterface gpsServiceInterface = Mockito.mock(GpsServiceInterface.class);

    @Before
    public void beforeEach() {

        gpsController = new GpsController(gpsServiceInterface);
    }

    @Test
    public void getUserLocation() throws JsonProcessingException {

        //GIVEN
        String userName = "userName";
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(0,0), new Date());

        //WHEN
        Mockito.when(gpsServiceInterface.getUserLocation(userName)).thenReturn(visitedLocation);

        //THEN
        Assert.assertTrue(gpsController.getLocation(userName).equals(objectMapper.writeValueAsString(visitedLocation)));
    }

    @Test
    public void getAttraction() throws JsonProcessingException {

        //GIVEN
        String attractionName = "attractionName";
        Attraction attraction = new Attraction(attractionName, "attractionCity", "attractionState", 0, 0);

        //WHEN
        Mockito.when(gpsServiceInterface.getAttraction(attractionName)).thenReturn(attraction);

        //THEN
        Assert.assertTrue(gpsController.getAttraction(attractionName).equals(objectMapper.writeValueAsString(attraction)));
    }

    @Test
    public void getAllAttraction() throws JsonProcessingException {

        //GIVEN
        Attraction attraction = new Attraction("attractionName", "attractionCity", "attractionState", 0, 0);
        List<Attraction> attractionList = new ArrayList<Attraction>();

        //WHEN
        Mockito.when(gpsServiceInterface.getAllAttraction()).thenReturn(attractionList);
        attractionList.add(attraction);

        //THEN
        Assert.assertTrue(gpsController.getAllAttraction().equals(objectMapper.writeValueAsString(attractionList)));
    }

    @Test
    public void getAllCurrentLocations() throws JsonProcessingException {

        //GIVEN
        String userName = "userName";
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(0,0), new Date());

        Map<UUID, VisitedLocation> visitedLocationList = new HashMap<UUID, VisitedLocation>();

        //WHEN
        Mockito.when(gpsServiceInterface.getAllCurrentLocations()).thenReturn(visitedLocationList);

        visitedLocationList.put(visitedLocation.getUserId(), visitedLocation);

        //THEN
        Assert.assertTrue(gpsController.getAllCurrentLocations().equals(objectMapper.writeValueAsString(gpsServiceInterface.getAllCurrentLocations())));
    }
}
