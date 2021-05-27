package unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reward.controller.RewardController;
import reward.service.RewardServiceInterface;

@SpringBootTest
public class RewardControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private RewardController rewardController;

    private RewardServiceInterface rewardServiceInterface = Mockito.mock(RewardServiceInterface.class);

    @Before
    public void beforeEach() {

        rewardController = new RewardController(rewardServiceInterface);
    }

    @Test
    public void getRewardPoints() throws JsonProcessingException {

        //GIVEN
        String userName = "userName";
        String attractionName = "attractionName";

        //WHEN
        Mockito.when(rewardServiceInterface.getRewardPoints(attractionName, userName)).thenReturn(10);

        //THEN
        Assert.assertTrue(rewardController.getRewardPoints(attractionName, userName).equals(objectMapper.writeValueAsString(10)));
    }
}