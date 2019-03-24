package mk.finki.ukim.emt.lab.persistance;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.models.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByUser(User user);
}
