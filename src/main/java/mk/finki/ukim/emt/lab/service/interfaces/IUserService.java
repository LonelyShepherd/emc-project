package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.results.PasswordResult;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;

public interface IUserService {
    User update(User user);
    User findByEmail(String email);
    UserResult create(RegisterViewModel user);
    UserResult generatePassword(String email);
    PasswordResult changePassword(User user, String currentPassword, String newPassword, String confirmNewPassword);
}
