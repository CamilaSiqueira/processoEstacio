package prova.prova.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import prova.prova.models.User;

import java.util.Optional;

/**
 * @author Camila Siqueira
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);
}
