package user.service;

import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import user.model.User;
import user.model.UserReward;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserServiceInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private static Map<String, User> userMap = new HashMap<>();

    @Override
    public void addUser(User user) {
        logger.info("addUser(" + user + ")");

        if (userMap.containsKey(user.getUserName()) == false) {

            userMap.put(user.getUserName(), user);
        }
    }

    @Override
    public void addToVisitedLocations(String userName, VisitedLocation visitedLocation) {
        logger.info("addToVisitedLocations(" + userName + "," + visitedLocation + ")");

        User user = userMap.get(userName);

        if (user != null) {

            user.getVisitedLocations().add(visitedLocation);
        }
    }

    @Override
    public void addUserReward(String userName, UserReward userReward) {
        logger.info("addUserReward(" + userName + "," + userReward + ")");

        User user = userMap.get(userName);

        if (user != null) {

            boolean alreadyExist = false;

            for (UserReward r : user.getUserRewards()) {

                if (r.getAttraction().equals(userReward.getAttraction())) {

                    alreadyExist = true;
                }
            }

            if (alreadyExist == false) {

                user.getUserRewards().add(userReward);
            }
        }
    }

    @Override
    public User getUser(String userName) {
        logger.info("getUser(" + userName + ")");

        User user = userMap.get(userName);

        if (user == null) {

            user = new User(null, null, null, null);
        }

        return user;
    }

    @Override
    public Map<String, User> getAllUser() {
        logger.info("getAllUser()");
        return userMap;
    }
}