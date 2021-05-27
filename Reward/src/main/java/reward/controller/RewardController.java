package reward.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reward.service.RewardService;
import reward.service.RewardServiceInterface;

@RestController
public class RewardController {

    private ObjectMapper objectMapper = new ObjectMapper();

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
    public String getRewardPoints(@RequestParam String attractionName, @RequestParam String userName) throws JsonProcessingException {
        logger.info("getRewardPoints(" + attractionName + "," + userName + ")");

        return objectMapper.writeValueAsString(rewardServiceInterface.getRewardPoints(attractionName, userName));
    }

    @RequestMapping("/calculateRewards")
    public String calculateRewards(@RequestParam String userName) throws JsonProcessingException {
        logger.info("calculateRewards(" + userName + ")");

        return objectMapper.writeValueAsString(rewardServiceInterface.calculateRewards(userName));
    }
}
