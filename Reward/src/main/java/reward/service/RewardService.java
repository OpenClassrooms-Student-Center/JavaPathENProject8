package reward.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;
import reward.model.User;
import reward.model.UserReward;
import rewardCentral.RewardCentral;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardService implements RewardServiceInterface {

    private RewardCentral rewardCentral;

    public RewardService() {

        rewardCentral = new RewardCentral();

    }

    public RewardService(RewardCentral rewardsCentral) {

        this.rewardCentral = rewardsCentral;
    }

    public List<UserReward> getUserRewards(User user) {
        return user.getUserRewards();
    }

    public List<UserReward> calculateRewards(User user, List<Attraction> attractionList) {

        List<UserReward> userRewardList = new ArrayList<UserReward>();

        List<VisitedLocation> userLocations = user.getVisitedLocations();

        for(VisitedLocation visitedLocation : userLocations) {

            for(Attraction attraction : attractionList) {

                if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {

                    int rewardPoints = rewardCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());

                    userRewardList.add(new UserReward(visitedLocation, attraction, rewardPoints));
                }
            }
        }

        return userRewardList;
    }
}
