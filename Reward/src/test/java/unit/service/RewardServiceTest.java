package unit.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void getRewardPoints() {

        //GIVEN
        UUID userId = UUID.randomUUID();
        UUID attractionId = UUID.randomUUID();

        //WHEN

        //THEN
        Assert.assertTrue(rewardService.getRewardPoints(attractionId, userId) >= 0);
    }
}
