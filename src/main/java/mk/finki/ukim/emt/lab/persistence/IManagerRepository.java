package mk.finki.ukim.emt.lab.persistence;

import mk.finki.ukim.emt.lab.models.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IManagerRepository extends JpaRepository<Manager, Integer> {
    Optional<Manager> findByEmail(String email);
}
