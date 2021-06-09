package gps.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gps.model.User;
import gps.model.UserNearestAttraction;
import gps.proxy.RewardProxy;
import gps.proxy.UserProxy;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.jackson.datatype.money.MoneyModule;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class allows to interact with a GpsUtil
 */
@Service
public class GpsService implements GpsServiceInterface {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    @Autowired
    private UserProxy userProxy;
    @Autowired
    private RewardProxy rewardProxy;

    private GpsUtil gpsUtil = new GpsUtil();

    /**
     * Creates a new GpsService
     */
    public GpsService() {
        logger.info("GpsService()");
    }

    /**
     * Creates a new GpsService with the specified UserProxy and RewardProxy
     * @param userProxy : UserProxy that this service will use
     * @param rewardProxy : RewardProxy that this service will use
     */
    public GpsService(UserProxy userProxy, RewardProxy rewardProxy) {
        logger.info("GpsService(" + userProxy + "," + rewardProxy + ")");

        this.userProxy = userProxy;
        this.rewardProxy = rewardProxy;
    }

    @Override
    public VisitedLocation getUserLocation(String userName) {
        logger.info("getUserLocation(" + userName + ")");

        VisitedLocation visitedLocation;

        User user = getUserFromProxy(userName);

        if (user.getVisitedLocations().size() <= 0) {

            visitedLocation = gpsUtil.getUserLocation(user.getUserId());

            String timeVisited = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss").format(visitedLocation.timeVisited);

            userProxy.addToVisitedLocations(userName, visitedLocation.location.longitude,
                    visitedLocation.location.latitude, timeVisited);

            rewardProxy.calculateRewards(userName);
        }

        else {

            visitedLocation = user.getVisitedLocations().get(user.getVisitedLocations().size()-1);
        }

        return visitedLocation;
    }

    @Override
    public Attraction getAttraction(String attractionName) {
        logger.info("getAttraction(" + attractionName + ")");

        Attraction attraction = null;

        for (Attraction a : gpsUtil.getAttractions()) {

            if (a.attractionName.equals(attractionName)) {

                attraction = a;
                break;
            }
        }

        return attraction;
    }

    @Override
    public Map<UUID, VisitedLocation> getAllCurrentLocations() {
        logger.info("getAllCurrentLocations()");

        Map<UUID, VisitedLocation> visitedLocationMap = new HashMap<>();

        for (User u : userProxy.getAllUser()) {

            visitedLocationMap.put(u.getUserId(), u.getVisitedLocations().get(u.getVisitedLocations().size()-1));
        }

        return visitedLocationMap;
    }

    @Override
    public List<UserNearestAttraction> getNearByAttractions(String userName) {
        logger.info("getNearByAttractions(" + userName + ")");

        User user = getUserFromProxy(userName);

        VisitedLocation visitedLocation = user.getVisitedLocations().get(user.getVisitedLocations().size()-1);

        Map<Double, Attraction> attractionMap = new TreeMap<Double, Attraction>();

        for (Attraction attraction : gpsUtil.getAttractions()) {

            double lat1 = Math.toRadians(attraction.latitude);
            double lon1 = Math.toRadians(attraction.longitude);
            double lat2 = Math.toRadians(visitedLocation.location.latitude);
            double lon2 = Math.toRadians(visitedLocation.location.longitude);

            double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                    + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

            double nauticalMiles = user.getUserPreferences().getAttractionProximity() * Math.toDegrees(angle);
            double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;

            attractionMap.put(statuteMiles, attraction);
        }

        List<UserNearestAttraction> userNearestAttractionList = new ArrayList<>();

        for (Map.Entry<Double, Attraction> entry : attractionMap.entrySet()) {

            String attractionName = entry.getValue().attractionName;
            Location attractionLocation = new Location(entry.getValue().latitude, entry.getValue().longitude);
            Location userLocation = visitedLocation.location;
            double attractionMilesDistance = entry.getKey();
            int rewardPoints = rewardProxy.getRewardPoints(attractionName, userName);

            userNearestAttractionList.add(new UserNearestAttraction(attractionName,
                    attractionLocation, userLocation, attractionMilesDistance, rewardPoints));

            if (userNearestAttractionList.size() >= 5) {

                break;
            }
        }

        return userNearestAttractionList;
    }

    private User getUserFromProxy(String userName) {

        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new MoneyModule());

        User user = null;

        try {

            user = objectMapper.readValue(userProxy.getUser(userName), User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
