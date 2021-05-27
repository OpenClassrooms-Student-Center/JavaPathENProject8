package user.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository implements UserRepositoryInterface {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private static Map<String, User> userMap = new HashMap<>();

    public UserRepository() {
        logger.info("UserRepository()");
    }

    @Override
    public void addUser(User user) {
        logger.info("addUser(" + user + ")");

        userMap.put(user.getUserName(), user);
    }

    @Override
    public User getUser(String userName) {
        logger.info("getUser(" + userName + ")");

        return userMap.get(userName);
    }

    @Override
    public List<User> getAllUser() {
        logger.info("getAllUser()");

        return new ArrayList<User>(userMap.values());
    }
}
