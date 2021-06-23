package unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gpsUtil.location.VisitedLocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import user.controller.UserController;
import user.model.User;
import user.service.UserServiceInterface;

import java.util.*;

@SpringBootTest
public class UserControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserController userController;

    private UserServiceInterface userServiceInterface = Mockito.mock(UserServiceInterface.class);

    @Before
    public void beforeEach() {

        userController = new UserController(userServiceInterface);
    }

    @Test
    public void addToVisitedLocations() {

        //GIVEN
        String userName = "userName";
        User user = Mockito.mock(User.class);
        VisitedLocation visitedLocation = Mockito.mock(VisitedLocation.class);

        //WHEN
        Mockito.when(userServiceInterface.getUser(userName)).thenReturn(user);
        Mockito.when(user.getUserId()).thenReturn(UUID.randomUUID());

        userController.addToVisitedLocations(userName, visitedLocation);

        //THEN
        Mockito.verify(userServiceInterface, Mockito.times(1)).addToVisitedLocations(userName, visitedLocation);
    }

    @Test
    public void getAllUser() throws JsonProcessingException {

        //GIVEN
        String userName = "userName";

        List<User> userList = new ArrayList<User>();

        //WHEN
        Mockito.when(userServiceInterface.getAllUser()).thenReturn(userList);

        //THEN
        Assert.assertTrue(userController.getAllUser().equals(objectMapper.writeValueAsString(userList)));
    }
}
