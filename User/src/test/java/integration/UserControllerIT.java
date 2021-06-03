package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import user.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MvcResult mvcResult;

    @Before
    public void beforeEach() {

        // GIVEN
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void addUserForm() throws Exception {

        // WHEN
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
                .param("userName", "userName")
                .param("phoneNumber", "phoneNumber")
                .param("emailAddress", "emailAddress")).andReturn();

        // THEN
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(2)
    public void addToVisitedLocations() throws Exception {

        // WHEN
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addToVisitedLocations")
                .param("userName", "userName")
                .param("longitude", "1.1")
                .param("latitude", "2.2")
                .param("timeVisited", "01/01/2021")).andReturn();

        // THEN
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(3)
    public void addUserReward() throws Exception {

        // WHEN
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addUserReward")
                .param("userName", "userName")
                .param("longitude", "1.1")
                .param("latitude", "2.2")
                .param("timeVisited", "01/01/2021")
                .param("attractionName", "attractionName")
                .param("attractionCity", "attractionCity")
                .param("attractionState", "attractionState")).andReturn();

        // THEN
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(4)
    public void setUserPreferences() throws Exception {

        // WHEN
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/setUserPreferences")
                .param("userName", "userName")
                .param("tripDuration", "7")
                .param("ticketQuantity", "5")
                .param("numberOfAdults", "2")
                .param("numberOfChildren", "3")
                .param("attractionProximity", "30")
                .param("highPricePoint", "10")
                .param("lowerPricePoint", "1")).andReturn();

        // THEN
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(5)
    public void getUser() throws Exception {

        // WHEN
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getUser")
                .param("userName", "userName")).andReturn();

        // THEN
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(6)
    public void getAllUser() throws Exception {

        // WHEN
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getAllUser")).andReturn();

        // THEN
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}