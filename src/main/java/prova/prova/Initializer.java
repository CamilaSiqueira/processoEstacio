package prova.prova;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import prova.prova.controllers.UserController;
import prova.prova.enums.RoleEnum;
import prova.prova.models.User;
import prova.prova.services.UserService;

import java.util.Optional;

/**
 * @author Camila Siqueira
 */

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Logger log = LoggerFactory.getLogger(UserController.class);
        User user;
        String id = "admin";

        log.info("Searching user admin");
        Optional<User> opUser = userService.findById(id);

        if (!opUser.isPresent()) {
            log.info("Saving user admin");
//            user = new User(id, "admin@admin.com", "Admin", PasswordUtils.generateBCrypt("123456"), 11111111111l, 21999028636l, User.Role.ADMIN);
            user = new User(id, "admin@admin.com", "Admin", "123456", "11111111111", "21999028636", RoleEnum.ADMIN);
            userService.save(user);
        }

        log.info("User admin ok");
    }
}