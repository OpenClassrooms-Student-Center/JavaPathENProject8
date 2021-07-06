package performance;

import gps.Application;
import org.apache.commons.lang3.time.StopWatch;
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

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class GpsControllerPerformance {

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

        int userNumber = 1000;
        int goodResultCount = 0;
        String userName = "userNameTest";
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        for (int i = 1; i <= userNumber; i++) {

            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getLocation")
                    .param("userName", userName)).andReturn();

            if (mvcResult.getResponse().getStatus() == 200) {

                goodResultCount++;
            }

            System.out.println("Getting location performed for the user " + i + "\n");
        }

        stopWatch.stop();

        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        Assert.assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));

        System.out.println("highVolumeTrackLocation: Good Result Counted: " + goodResultCount + "\n");
        Assert.assertEquals(goodResultCount, userNumber);
    }
}