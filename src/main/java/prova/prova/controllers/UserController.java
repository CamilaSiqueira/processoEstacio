package prova.prova.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import prova.prova.models.User;
import prova.prova.services.UserService;

import java.util.List;
import java.util.Optional;

/**
 * @author Camila Siqueira
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> findAll() {
        List<User> lista = userService.findAll();

        System.err.println("Listando todos" + lista.size());

        return userService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public User save(@RequestBody User user) {
        System.err.println("Salvando user");
        return userService.save(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        System.err.println("Atualizando user");
        User userU = userService.save(user);
        System.err.println("User" + userU.getId());
        return userU;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteById(@RequestParam("id") String id) {
        System.err.println("Deletando user");
        userService.deleteById(id);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Optional<User> getUserById(@PathVariable("id") String id) {
        System.err.println("Buscando user pelo id");
        return userService.findById(id);
    }

    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public User getUserByEmail(@PathVariable("email") String email) {
        System.err.println("Buscando user pelo email");
        return userService.findByEmail(email);
    }
}
