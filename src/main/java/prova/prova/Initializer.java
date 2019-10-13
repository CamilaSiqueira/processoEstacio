package prova.prova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import prova.prova.models.User;
import prova.prova.services.UserService;

import java.util.Optional;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.err.println("--------------------------------------------- Começou");
        User user;
        String id = "admin";

        Optional<User> opUser = userService.findById(id);

        if (!opUser.isPresent()) {
            user = new User(id, "admin@admin.com", "Admin", "123456", 11111111111l, 21999028636l, User.Role.ADMIN);
            userService.save(user);
            System.err.println("Salvando Usuário Admin");
        } else {
            System.err.println("Usuário presente");
        }

        System.err.println("Iniciado");
    }
}