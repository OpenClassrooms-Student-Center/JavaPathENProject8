package reward.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reward.model.User;
import reward.model.UserReward;
import reward.proxy.GpsProxy;
import reward.proxy.UserProxy;
import rewardCentral.RewardCentral;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RewardService implements RewardServiceInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private UserProxy userProxy;
    private GpsProxy gpsProxy;

    private RewardCentral rewardCentral;

    public RewardService() {
        logger.info("RewardService()");

        rewardCentral = new RewardCentral();
    }

    public RewardService(RewardCentral rewardsCentral) {
        logger.info("RewardService(" + rewardsCentral + ")");

        this.rewardCentral = rewardsCentral;
    }

    public int getRewardPoints(String attractionName, String userName) {
        logger.info("getRewardPoints(" + attractionName + "," + userName + ")");

        Attraction attraction = gpsProxy.getAttraction(attractionName);
        User user = userProxy.getUser(userName);

        return rewardCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
    }

    public List<UserReward> calculateRewards(String userName) {
        logger.info("calculateRewards(" + userName + ")");

        User user = userProxy.getUser(userName);

        List<Attraction> attractionList = gpsProxy.getNearByAttractions();
        List<VisitedLocation> userLocations = user.getVisitedLocations();
        List<UserReward> userRewardList = new ArrayList<UserReward>();

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