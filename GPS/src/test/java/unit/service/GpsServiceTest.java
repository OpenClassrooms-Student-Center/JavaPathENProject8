package unit.service;

import gps.model.User;
import gps.proxy.RewardProxy;
import gps.proxy.UserProxy;
import gps.service.GpsService;
import gpsUtil.location.VisitedLocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class GpsServiceTest {

    private GpsService gpsService;

    private UserProxy userProxy = Mockito.mock(UserProxy.class);
    private RewardProxy rewardProxy = Mockito.mock(RewardProxy.class);

    @Before
    public void beforeEach() {

        gpsService = new GpsService(userProxy, rewardProxy);
    }

    @Test
    public void getUserLocation() {

        //GIVEN
        String userName = "userName";
        User user = Mockito.mock(User.class);
        VisitedLocation visitedLocation = Mockito.mock(VisitedLocation.class);

        List<VisitedLocation> visitedLocationList = new ArrayList<VisitedLocation>();

        //WHEN
        Mockito.when(userProxy.getUser(userName)).thenReturn(user);
        Mockito.when(user.getUserId()).thenReturn(UUID.randomUUID());
        Mockito.when(user.getVisitedLocations()).thenReturn(visitedLocationList);

        //THEN
        Assert.assertTrue(gpsService.getUserLocation(userName) == visitedLocation);
    }

    @Test
    public void getAttraction() {

        //GIVEN
        String attractionName = "Disneyland";

        //WHEN

        //THEN
        Assert.assertTrue(gpsService.getAttraction(attractionName).attractionName == attractionName);
    }

    @Test
    public void getAllCurrentLocations() {

        //GIVEN
        UUID userId = UUID.randomUUID();

        User user = Mockito.mock(User.class);
        List<User> userList = new ArrayList<User>();

        VisitedLocation visitedLocation = Mockito.mock(VisitedLocation.class);
        List<VisitedLocation> visitedLocationList = new ArrayList<VisitedLocation>();

        //WHEN
        Mockito.when(userProxy.getAllUser()).thenReturn(userList);
        userList.add(user);

        Mockito.when(user.getUserId()).thenReturn(userId);
        Mockito.when(user.getVisitedLocations()).thenReturn(visitedLocationList);
        visitedLocationList.add(visitedLocation);

        //THEN
        Assert.assertTrue(gpsService.getAllCurrentLocations().get(userId) == visitedLocation);
    }
}
