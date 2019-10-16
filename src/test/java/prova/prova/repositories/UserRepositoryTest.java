package prova.prova.repositories;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import prova.prova.enums.RoleEnum;
import prova.prova.models.User;
import prova.prova.utils.PasswordUtils;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final String ID = "test";
    private final String EMAIL = "test@test.com";
    private final String CPF = "99999999999";

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User(ID, EMAIL, "Test", PasswordUtils.generateBCrypt("123456"), "99999999999", "11111111111", RoleEnum.ROLE_USER);
        userRepository.save(user);
    }

    @Test
    public void testFindById() {
        Optional<User> userOp = userRepository.findById(ID);

        if (userOp.isPresent()) {
            Assert.assertEquals(ID, userOp.get().getId());
        } else {
            Assert.assertTrue(userOp.isPresent());
        }
    }

    @Test
    public void testFindByEmail() {
        Optional<User> userOp = userRepository.findByEmail(EMAIL);

        if (userOp.isPresent()) {
            Assert.assertEquals(EMAIL, userOp.get().getEmail());

        } else {
            Assert.assertTrue(userOp.isPresent());
        }
    }

    @Test
    public void testFindByCpf() {
        Optional<User> userOp = userRepository.findByCpf(CPF);

        if (userOp.isPresent()) {
            Assert.assertEquals(CPF, userOp.get().getCpf());
        } else {
            Assert.assertTrue(userOp.isPresent());

        }
    }

    @After
    public final void tearDown() {
        this.userRepository.delete(user);
    }

}
