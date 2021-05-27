package user.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import user.model.User;
import user.model.UserReward;
import user.repository.UserRepository;
import user.repository.UserRepositoryInterface;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private UserRepositoryInterface userRepositoryInterface;

    public UserService() {
        logger.info("UserService()");

        userRepositoryInterface = new UserRepository();
    }

    public UserService(UserRepositoryInterface userRepositoryInterface) {
        logger.info("UserService(" + userRepositoryInterface + ")");

        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Override
    public void addUser(User user) {
        logger.info("addUser(" + user + ")");

        userRepositoryInterface.addUser(user);
    }

    @Override
    public void addToVisitedLocations(String userName, VisitedLocation visitedLocation) {
        logger.info("addToVisitedLocations(" + userName + "," + visitedLocation + ")");

        User user = userRepositoryInterface.getUser(userName);

        if (user != null) {

            user.getVisitedLocations().add(visitedLocation);
        }
    }

    @Override
    public void addUserReward(String userName, UserReward userReward) {
        logger.info("addUserReward(" + userName + "," + userReward + ")");

        User user = userRepositoryInterface.getUser(userName);

        if (user != null) {

            boolean alreadyExist = false;

            for (UserReward r : user.getUserRewards()) {

                if (r.getAttraction().attractionName.equals(userReward.getAttraction().attractionName)) {

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

        return userRepositoryInterface.getUser(userName);
    }

    @Override
    public List<User> getAllUser() {
        logger.info("getAllUser()");

        return userRepositoryInterface.getAllUser();
    }
}