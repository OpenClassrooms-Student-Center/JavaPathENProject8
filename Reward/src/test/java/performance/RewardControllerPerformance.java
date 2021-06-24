package performance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import reward.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class RewardControllerPerformance {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MvcResult mvcResult;

    @Before
    public void beforeEach() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void highVolumeTrackLocation() throws Exception {

        int userNumber = 100000;
        int timeLimit = 20*60*1000;

        long startTime = System.currentTimeMillis();

        String userName = "userNameTest";

        for (int i = 0; i <= userNumber; i++) {

            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/calculateRewards")
                    .param("userName", userName)).andReturn();

            System.out.println("Calculate Rewards performed for the user number " + i );
        }

        long timeElapsed = System.currentTimeMillis() - startTime;

        System.out.println("The time elapsed for calculate rewards of " + userNumber + " users is to : " + (timeElapsed/1000) + " seconds.");
        Assert.assertTrue(timeElapsed <= timeLimit);
    }
}