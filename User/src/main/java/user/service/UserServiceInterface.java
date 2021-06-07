package user.service;


import gpsUtil.location.VisitedLocation;
import user.model.User;
import user.model.UserPreferences;
import user.model.UserReward;

import java.util.List;

/**
 * This interface allows to implement user service methods
 */
public interface UserServiceInterface {

    /**
     * Add an User
     * @param user : data of the User to add
     */
    public void addUser(User user);

    /**
     * Add a VisitedLocation to an User
     * @param userName : Name of the User to found
     * @param visitedLocation : VisitedLocation to add to found User
     */
    public void addToVisitedLocations(String userName, VisitedLocation visitedLocation);

    /**
     * Add a UserReward to an User
     * @param userName : Name of the User to found
     * @param userReward : UserReward to add to found User
     */
    public void addUserReward(String userName, UserReward userReward);

    /**
     * Set the UserPreferences of an User
     * @param userName : Name of the User to found
     * @param userPreferences : UserPreferences to set on the found User
     */
    public void setUserPreferences(String userName, UserPreferences userPreferences);

    /**
     * Get an User
     * @param userName : Name of the User to found
     * @return The User that was found
     */
    public User getUser(String userName);

    /**
     * Get the User list
     * @return The User list
     */
    public List<User> getAllUser();
}