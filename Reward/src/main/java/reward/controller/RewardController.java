package reward.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reward.service.RewardService;
import reward.service.RewardServiceInterface;

/**
 * This class allows to intercept reward requests
 */
@RestController
public class RewardController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    @Autowired
    private RewardServiceInterface rewardServiceInterface;

    @Autowired
    private RewardService rewardService;

    /**
     * Creates a new RewardController
     */
    public RewardController() {
        logger.info("RewardController()");

        rewardServiceInterface = rewardService;
    }

    /**
     * Creates a new RewardController with the specified RewardServiceInterface
     * @param rewardServiceInterface : service that this controller will use
     */
    public RewardController(RewardServiceInterface rewardServiceInterface) {
        logger.info("RewardController(" + rewardServiceInterface + ")");

        this.rewardServiceInterface = rewardServiceInterface;
    }

    /**
     * Intercepts the reward points getting request
     * @param attractionName : Name of the Attraction to use for calculate reward points
     * @param userName : Name of the User to use for calculate reward points
     * @return The reward points amount (JSon)
     */
    @GetMapping("/getRewardPoints")
    public String getRewardPoints(@RequestParam String attractionName, @RequestParam String userName) throws JsonProcessingException {
        logger.info("getRewardPoints(" + attractionName + "," + userName + ")");

        return objectMapper.writeValueAsString(rewardServiceInterface.getRewardPoints(attractionName, userName));
    }

    /**
     * Intercepts the rewards calculating request
     * @param userName : Name of the User to use for creating the UserReward list
     * @return The UserReward list (JSon)
     */
    @GetMapping("/calculateRewards")
    public String calculateRewards(@RequestParam String userName) throws JsonProcessingException {
        logger.info("calculateRewards(" + userName + ")");

        return objectMapper.writeValueAsString(rewardServiceInterface.calculateRewards(userName));
    }
}