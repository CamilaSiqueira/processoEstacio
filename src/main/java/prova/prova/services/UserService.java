package prova.prova.services;

import org.springframework.stereotype.Service;
import prova.prova.models.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Camila Siqueira
 */

@Service
public interface UserService {
    List<User> findAll();

    User save(User user);

    void deleteById(String userId);

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    Optional<User> findById(String id);
}
