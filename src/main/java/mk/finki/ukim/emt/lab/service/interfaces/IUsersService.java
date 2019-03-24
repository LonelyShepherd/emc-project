package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface IUsersService {
    UserResult create(RegisterViewModel user) throws Exception;
    Collection<User> listAll();
}
