package TripPricer.service;

import tripPricer.Provider;

import java.util.List;

/**
 * This interface allows to implement trip pricer service methods
 */
public interface TripPricerServiceInterface {

    /**
     * Get the Provider list
     * @param userName : Name of the User to found
     * @return The Provider list
     */
    public List<Provider> getTripDeals(String userName);
}