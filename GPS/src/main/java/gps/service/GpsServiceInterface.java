package gps.service;

import gps.model.UserNearestAttraction;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public interface GpsServiceInterface {

    public VisitedLocation getUserLocation(String userName);

    public Map<UUID, VisitedLocation> getAllCurrentLocations();

    public Attraction getAttraction(String attractionName);

    public List<UserNearestAttraction> getNearByAttractions(String userName, VisitedLocation visitedLocation);

}
