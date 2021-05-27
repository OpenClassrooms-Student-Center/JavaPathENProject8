package user.service;


import gpsUtil.location.VisitedLocation;
import user.model.User;
import user.model.UserReward;

import java.util.List;

public interface UserServiceInterface {

    public void addUser(User user);

    public void addToVisitedLocations(String userName, VisitedLocation visitedLocation);

    public void addUserReward(String userName, UserReward userReward);

    public User getUser(String userName);

    public List<User> getAllUser();
}
