package reward.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import reward.model.User;
import reward.model.UserReward;
import rewardCentral.RewardCentral;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardService implements RewardServiceInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private RewardCentral rewardCentral;

    public RewardService() {
        logger.info("RewardService()");

        rewardCentral = new RewardCentral();

    }

    public RewardService(RewardCentral rewardsCentral) {
        logger.info("RewardService(" + rewardsCentral + ")");

        this.rewardCentral = rewardsCentral;
    }

    public List<UserReward> getUserRewards(User user) {
        logger.info("getUserRewards(" + user + ")");

        return user.getUserRewards();
    }

    public List<UserReward> calculateRewards(User user, List<Attraction> attractionList) {
        logger.info("calculateRewards(" + user + "," + attractionList + ")");

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