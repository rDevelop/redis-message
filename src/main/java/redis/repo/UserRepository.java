package redis.repo;


import redis.model.User;

import java.util.Map;

public interface UserRepository {

    void saveUser(User user);

    void updateUser(User User);

    User findUser(long id);

    Map<Object, Object> findAllUsers();

    void deleteUser(long id);

    void listAllUsers();
}
