package prova.prova.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import prova.prova.models.User;

/**
 * @author Camila Siqueira
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}
