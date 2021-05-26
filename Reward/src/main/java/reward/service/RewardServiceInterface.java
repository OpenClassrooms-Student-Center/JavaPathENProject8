package reward.service;

import org.springframework.web.bind.annotation.RequestParam;
import reward.model.UserReward;

import java.util.List;
import java.util.UUID;

public interface RewardServiceInterface {

    public int getRewardPoints(String attractionName, String userName);

    public List<UserReward> calculateRewards(String userName);
}
