package gps.service;

import gps.model.User;
import gps.model.UserNearestAttraction;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GpsServiceInterface {

    public VisitedLocation getUserLocation(String userName);

    public Map<UUID, VisitedLocation> getAllCurrentLocations();

    public Attraction getAttraction(String attractionName);

    public List<UserNearestAttraction> getNearByAttractions(String userName, VisitedLocation visitedLocation);

}
