package user.service;

import user.model.User;

import java.util.ArrayList;

public interface UserServiceInterface {

    /**
     * Create a User
     * @param user : data of the User to create
     */
    public void createUser(User user);

    /**
     * Read a User
     * @param userName : user name of the User to read
     * @return The User that was read
     */
    public User readUser(String userName);

    /**
     * Update a User
     * @param userName : user name of the User to update
     * @param user : data of the User to update
     */
    public void updateUser(String userName, User user);

    /**
     * Delete a User
     * @param userName : user name of the User to delete
     */
    public void deleteUser(String userName);
}
