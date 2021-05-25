package reward.service;

import gpsUtil.location.Attraction;
import reward.model.User;
import reward.model.UserReward;

import java.util.List;

public interface RewardServiceInterface {

    public List<UserReward> getUserRewards(User user);

    public List<UserReward> calculateRewards(User user, List<Attraction> attractionList);
}
