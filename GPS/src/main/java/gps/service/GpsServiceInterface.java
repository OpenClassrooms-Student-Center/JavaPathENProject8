package gps.service;

import gps.model.UserNearestAttraction;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This interface allows to implement gps service methods
 */
@Component
public interface GpsServiceInterface {

    /**
     * Get an user VisitedLocation
     * @param userName : Name of the User to use for getting his location
     * @return The VisitedLocation of the User
     */
    public VisitedLocation getUserLocation(String userName);

    /**
     * Get all users VisitedLocation
     * @return The Map of all user id/location
     */
    public Map<UUID, VisitedLocation> getAllCurrentLocations();

    /**
     * Get an Attraction
     * @param attractionName : Name of the Attraction to found
     * @return The Attraction found
     */
    public Attraction getAttraction(String attractionName);

    /**
     * Get all Attraction
     * @return The List of all Attraction
     */
    public List<Attraction> getAllAttraction();

    /**
     * Get the list of the nearest Attraction
     * @param userName : Name of the User to use for getting the nearest Attraction
     * @return The Attraction found
     */
    public List<UserNearestAttraction> getNearByAttractions(String userName);
}