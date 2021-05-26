package reward.controller;

import com.jsoniter.output.JsonStream;
import gpsUtil.location.Attraction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reward.model.User;
import reward.proxy.GpsProxy;
import reward.proxy.UserProxy;
import reward.service.RewardService;
import reward.service.RewardServiceInterface;

import java.util.List;
import java.util.UUID;

@RestController
public class RewardController {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private RewardServiceInterface rewardServiceInterface;

    public RewardController() {
        logger.info("RewardController()");

        rewardServiceInterface = new RewardService();
    }

    public RewardController(RewardServiceInterface rewardServiceInterface) {
        logger.info("RewardController(" + rewardServiceInterface + ")");

        this.rewardServiceInterface = rewardServiceInterface;
    }

    @RequestMapping("/getRewardPoints")
    public String getRewardPoints(@RequestParam String attractionName, @RequestParam String userName) {
        logger.info("getRewardPoints(" + attractionName + "," + userName + ")");

        return JsonStream.serialize(rewardServiceInterface.getRewardPoints(attractionName, userName));
    }

    @RequestMapping("/calculateRewards")
    public String calculateRewards(@RequestParam String userName) {
        logger.info("calculateRewards(" + userName + ")");

        return JsonStream.serialize(rewardServiceInterface.calculateRewards(userName));
    }
}
