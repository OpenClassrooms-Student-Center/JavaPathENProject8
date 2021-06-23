package reward.service;

import org.springframework.web.bind.annotation.RequestParam;
import reward.model.UserReward;

import java.util.List;
import java.util.UUID;

public interface RewardServiceInterface {

    /**
     * Get the reward points amount of an attraction
     * @param attractionId : Id of the attraction
     * @param userId : Id of the User to found
     * @return The User that was found
     */
    public int getRewardPoints(UUID attractionId, UUID userId);

    /**
     * Get the UserReward list
     * @return The UserReward list
     */
    public List<UserReward> calculateRewards(String userName);
}