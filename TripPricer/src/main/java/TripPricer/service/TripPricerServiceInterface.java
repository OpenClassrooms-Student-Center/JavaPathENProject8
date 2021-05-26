package TripPricer.service;

import tripPricer.Provider;

import java.util.List;

public interface TripPricerServiceInterface {

    public List<Provider> getTripDeals(String userName);
}
