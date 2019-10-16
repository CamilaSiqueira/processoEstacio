package prova.prova.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import prova.prova.models.User;
import prova.prova.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(userRepository.findById(Mockito.anyString())).willReturn(Optional.of(new User()));
        BDDMockito.given(userRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(new User()));
        BDDMockito.given(userRepository.findByCpf(Mockito.anyString())).willReturn(Optional.of(new User()));
        BDDMockito.given(userRepository.findAll()).willReturn(new ArrayList<User>());
        BDDMockito.given(userRepository.save(Mockito.any(User.class))).willReturn(new User());
    }

    @Test
    public void testFindAll() {
        List<User> users = userService.findAll();
        Assert.assertNotNull(users);
        Assert.assertTrue(users.isEmpty());
    }

    @Test
    public void testFindById() {
        Optional<User> userOp = userService.findById("admin");
        Assert.assertTrue(userOp.isPresent());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> userOp = userService.findByEmail("admin@admin.com");
        Assert.assertTrue(userOp.isPresent());
    }

    @Test
    public void testPersist() {
        User user = userService.save(new User());
        Assert.assertNotNull(user);
    }
}
