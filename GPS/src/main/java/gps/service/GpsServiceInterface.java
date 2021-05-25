package gps.service;

import gps.model.User;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

import java.util.List;

public interface GpsServiceInterface {

    public VisitedLocation getUserLocation(User user);

    public List<VisitedLocation> getAllCurrentLocations();

    public List<Attraction> getAttractionList();

    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

}
