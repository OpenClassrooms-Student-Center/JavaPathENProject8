package TripPricer.service;

import TripPricer.model.User;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;

@Service
public class TripPricerService implements TripPricerServiceInterface {

    private static final String tripPricerApiKey = "test-server-api-key";

    private TripPricer tripPricer;

    public TripPricerService() {

        tripPricer = new TripPricer();
    }

    public TripPricerService(TripPricer tripPricer) {

        this.tripPricer = tripPricer;
    }

    public List<Provider> getTripDeals(User user) {

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
