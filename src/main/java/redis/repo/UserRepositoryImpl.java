package redis.repo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.model.User;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String KEY = "User";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public UserRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void saveUser(final User User) {
        hashOperations.put(KEY, User.getId(), User);
    }

    public void updateUser(final User User) {
        hashOperations.put(KEY, User.getId(), User);
    }

    public User findUser(final long id) {
        return (User) hashOperations.get(KEY, id);
    }

    public Map<Object, Object> findAllUsers() {
        return hashOperations.entries(KEY);
    }

    public void deleteUser(final long id) {
        hashOperations.delete(KEY, id);
    }

    public void listAllUsers() {
        final Map<Object, Object> keys = hashOperations.entries(KEY);
        for (Map.Entry<Object, Object> entry : keys.entrySet()) {
            LOGGER.info("{}:{} ", entry.getKey(), entry.getValue());
        }
    }
}