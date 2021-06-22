package gps.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This interface allows to send requests to the reward api
 */
@Component
@FeignClient(name = "reward-api", url = "localhost:8082")
public interface RewardProxy {

    /**
     * Send the reward points getting request
     * @param attractionName : Name of the Attraction to use for calculate reward points
     * @param userName : Name of the User to use for calculate reward points
     * @return The reward points amount
     */
    @GetMapping(value = "/getRewardPoints", produces = "application/json")
    public int getRewardPoints(@RequestParam String attractionName, @RequestParam String userName);

    /**
     * Send the rewards calculating request
     * @param userName : Name of the User to use for creating the UserReward list
     * @return The UserReward list
     */
    @PostMapping(value = "/calculateRewards", produces = "application/json")
    public void calculateRewards(@RequestParam String userName);
}
