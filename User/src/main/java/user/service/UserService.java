package user.service;

import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;
import user.model.User;
import user.model.UserReward;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {

    private static Map<String, User> userMap = new HashMap<>();

    @Override
    public void addUser(User user) {

        if (userMap.containsKey(user.getUserName()) == false) {

            userMap.put(user.getUserName(), user);
        }
    }

    @Override
    public void addToVisitedLocations(String userName, VisitedLocation visitedLocation) {

        User user = userMap.get(userName);

        if (user != null) {

            user.getVisitedLocations().add(visitedLocation);
        }
    }

    @Override
    public void addUserReward(String userName, UserReward userReward) {

        User user = userMap.get(userName);

        if (user != null) {

            boolean alreadyExist = false;

            for (UserReward r : user.getUserRewards()) {

                if (r.attraction.attractionName.equals(userReward.attraction)) {

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

        User user = userMap.get(userName);

        if (user == null) {

            user = new User(null, null, null, null);
        }

        return user;
    }

    @Override
    public Map<String, User> getAllUser() {
        return userMap;
    }
}