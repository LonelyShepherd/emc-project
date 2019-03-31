package mk.finki.ukim.emt.lab.persistence;

import mk.finki.ukim.emt.lab.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
