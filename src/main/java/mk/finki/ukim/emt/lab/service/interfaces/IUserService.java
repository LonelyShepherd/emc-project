package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.results.PasswordResult;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;

public interface IUserService {
    User findByEmail(String email);
    UserResult generatePassword(String email);
    UserResult register(RegisterViewModel user);
    User updatePassword(User user, String password);
    User updateUserInfo(User user, String firstName, String lastName, String email);
    PasswordResult changePassword(User user, String currentPassword, String newPassword, String confirmNewPassword);
}
