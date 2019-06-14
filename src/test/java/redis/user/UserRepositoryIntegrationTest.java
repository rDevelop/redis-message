package redis.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.config.RedisConfig;
import redis.model.User;
import redis.repo.UserRepository;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class UserRepositoryIntegrationTest {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Test.class);


    @Autowired
    private UserRepository UserRepository;

    @Test
    public void whenSavingUser_thenAvailableOnRetrieval() throws Exception {
        final User User = new User(1, "John", "Doe", "jdoh@dohhead.com");
        UserRepository.saveUser(User);
        final User retrievedUser = UserRepository.findUser(User.getId());
        assertEquals(User.getId(), retrievedUser.getId());
    }

    @Test
    public void whenUpdatingUser_thenAvailableOnRetrieval() throws Exception {
        final User User = new User(2, "John", "Doe", "");
        UserRepository.saveUser(User);
        User.setFirstName("Richard");
        User.setLastName("Watson");
        UserRepository.saveUser(User);
        final User retrievedUser = UserRepository.findUser(User.getId());
        LOGGER.info("{}:{} ", retrievedUser.getId(), retrievedUser.getFirstName());

        assertEquals(User.getFirstName(), retrievedUser.getFirstName());
    }

    @Test
    public void whenSavingUsers_thenAllShouldAvailableOnRetrieval() throws Exception {
        final User engUser = new User(2, "John Doe", "Doe", "jdoh@dohhead.com");
        final User medUser = new User(3, "Gareth",  "Houston", "gar@gar.com");
        UserRepository.saveUser(engUser);
        UserRepository.saveUser(medUser);
        final Map<Object, Object> retrievedUser = UserRepository.findAllUsers();
        assertEquals(retrievedUser.size(), 3);
    }



    @Test
    public void whenDeletingUser_thenNotAvailableOnRetrieval() throws Exception {
        final User User = new User(66, "John", "Doe", "jo@dom.com");
        UserRepository.saveUser(User);
        UserRepository.deleteUser(User.getId());
        final User retrievedUser = UserRepository.findUser(User.getId());
        assertNull(retrievedUser);
    }
}