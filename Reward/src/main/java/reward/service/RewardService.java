package reward.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reward.model.User;
import reward.model.UserReward;
import reward.proxy.GpsProxy;
import reward.proxy.UserProxy;
import rewardCentral.RewardCentral;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows to interact with a RewardCentral
 */
@Service
public class RewardService implements RewardServiceInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    @Autowired
    private UserProxy userProxy;
    @Autowired
    private GpsProxy gpsProxy;

    private RewardCentral rewardCentral = new RewardCentral();

    /**
     * Creates a new RewardService
     */
    public RewardService() {
        logger.info("RewardService()");
    }

    /**
     * Creates a new RewardService with the specified UserProxy and GpsProxy
     * @param userProxy : UserProxy that this service will use
     * @param gpsProxy : GpsProxy that this service will use
     */
    public RewardService(UserProxy userProxy, GpsProxy gpsProxy) {
        logger.info("RewardService(" + userProxy + "," + gpsProxy + ")");

        this.userProxy = userProxy;
        this.gpsProxy = gpsProxy;
    }

    @Override
    public int getRewardPoints(String attractionName, String userName) {
        logger.info("getRewardPoints(" + attractionName + "," + userName + ")");

        int rewardPoint = -1;

        Attraction attraction = gpsProxy.getAttraction(attractionName);
        User user = userProxy.getUser(userName);

        if (attraction != null && user != null) {

            rewardPoint = rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());
        }

        return rewardPoint;
    }

    @Override
    public List<UserReward> calculateRewards(String userName) {
        logger.info("calculateRewards(" + userName + ")");

        List<UserReward> userRewardList = new ArrayList<UserReward>();

        User user = userProxy.getUser(userName);

        if (user != null) {

            for(VisitedLocation visitedLocation : user.getVisitedLocations()) {

                for(Attraction attraction : gpsProxy.getAllAttraction()) {

                    if (user.getUserRewards().stream().filter(r -> r.getAttraction().equals(attraction.getAttractionName())).count() == 0) {

                        int rewardPoints = rewardCentral.getAttractionRewardPoints(attraction.getAttractionId(), user.getUserId());

                        userRewardList.add(new UserReward(visitedLocation, attraction, rewardPoints));
                    }
                }
            }
        }

        return userRewardList;
    }
}