package reward.service;

import org.springframework.web.bind.annotation.RequestParam;
import reward.model.UserReward;

import java.util.List;
import java.util.UUID;

public interface RewardServiceInterface {

    /**
     * Get the reward points amount of an attraction
     * @param attractionName : Name of the attraction
     * @param userName : Name of the User to found
     * @return The User that was found
     */
    public int getRewardPoints(String attractionName, String userName);

    /**
     * Get the UserReward list
     * @return The UserReward list
     */
    public List<UserReward> calculateRewards(String userName);
}