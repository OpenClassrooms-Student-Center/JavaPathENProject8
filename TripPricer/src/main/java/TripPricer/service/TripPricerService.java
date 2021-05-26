package TripPricer.service;

import TripPricer.model.User;
import TripPricer.proxy.UserProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;

@Service
public class TripPricerService implements TripPricerServiceInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private static final String tripPricerApiKey = "test-server-api-key";

    private UserProxy userProxy;

    private TripPricer tripPricer;

    public TripPricerService() {
        logger.info("TripPricerService()");

        tripPricer = new TripPricer();
    }

    public TripPricerService(TripPricer tripPricer) {
        logger.info("TripPricerService(" + tripPricer + ")");

        this.tripPricer = tripPricer;
    }

    public List<Provider> getTripDeals(String userName) {
        logger.info("getTripDeals(" + userName + ")");

        User user = userProxy.getUser(userName);

        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();

        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey,
                user.getUserId(),
                user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(),
                user.getUserPreferences().getTripDuration(),
                cumulativeRewardPoints);

        return providers;
    }
}