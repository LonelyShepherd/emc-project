package mk.finki.ukim.emt.lab.persistance;

import mk.finki.ukim.emt.lab.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<User, Integer> {

}
