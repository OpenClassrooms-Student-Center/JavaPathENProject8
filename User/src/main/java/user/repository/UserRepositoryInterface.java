package user.repository;

import user.model.User;

import java.util.List;

public interface UserRepositoryInterface {

    public void addUser(User user);
    public User getUser(String userName);
    public List<User> getAllUser();
}
