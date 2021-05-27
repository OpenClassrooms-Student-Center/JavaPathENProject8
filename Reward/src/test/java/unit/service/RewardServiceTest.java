package unit.service;

import gpsUtil.location.Attraction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reward.model.User;
import reward.proxy.GpsProxy;
import reward.proxy.UserProxy;
import reward.service.RewardService;

import java.util.UUID;

@SpringBootTest
public class RewardServiceTest {

    private RewardService rewardService;

    private UserProxy userProxy = Mockito.mock(UserProxy.class);
    private GpsProxy gpsProxy = Mockito.mock(GpsProxy.class);

    @Before
    public void beforeEach() {

        rewardService = new RewardService(userProxy, gpsProxy);
    }

    @Test
    public void getAttraction() {

        //GIVEN
        String userName = "userName";
        String attractionName = "attractionName";

        User user = Mockito.mock(User.class);
        Attraction attraction = new Attraction(attractionName, "attractionCity", "attractionState", 0, 0);

        //WHEN
        Mockito.when(gpsProxy.getAttraction(attractionName)).thenReturn(attraction);
        Mockito.when(userProxy.getUser(userName)).thenReturn(user);

        Mockito.when(user.getUserId()).thenReturn(UUID.randomUUID());

        //THEN
        Assert.assertTrue(rewardService.getRewardPoints(attractionName, userName) >= 1);
    }
}
