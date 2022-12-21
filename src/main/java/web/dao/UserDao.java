package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    User getUser(int id);

    List<User> getAllUsers();

    void edit(int id, User user);

    void add(User user);

    void delete(int id);

}
