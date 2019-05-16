package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.consts.Role;
import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IEmailService;
import mk.finki.ukim.emt.lab.service.interfaces.IUserService;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationTokenService;
import mk.finki.ukim.emt.lab.service.results.PasswordResult;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final IEmailService _emailService;
    private final PasswordEncoder _passwordEncoder;
    private final IUserRepository _userRepository;
    private final IVerificationTokenService _verificationTokenService;

    public UserService(
        IEmailService emailService,
        PasswordEncoder passwordEncoder,
        IUserRepository usersRepository,
        IVerificationTokenService verificationTokenService) {
        _emailService = emailService;
        _passwordEncoder = passwordEncoder;
        _userRepository = usersRepository;
        _verificationTokenService = verificationTokenService;
    }

    @Override
    public User updateUserInfo(User user, String firstName, String lastName, String email) {
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;

        return _userRepository.save(user);
    }

    @Override
    public List<User> listAll() {
        return _userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return _userRepository.findById(id).orElseGet(null);
    }

    @Override
    public void deleteById(int id) {
        _userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return _userRepository
                .findByEmail(email)
                .orElse(null);
    }

    @Override
    public UserResult generatePassword(String email) {
        if (email == "")
            return UserResult.failed("The email field is required");

        User user = findByEmail(email);
        String password = UUID.randomUUID().toString().replace("-", "");

        if (user == null)
            return UserResult.failed("There is not user with that email address");

        User updated = updatePassword(user, password);

        if (updated != null) {
            try {
                _emailService.send(user, "Password Reset", "<p>Your new password is: <b>" + password + "</b>");
            } catch (Exception e) {
                return UserResult.failed("The email failed to be sent");
            }

            return UserResult.success("An email with the new password has been sent to your email address");
        }

        return UserResult.failed("We couldn't send you the new password for your account, please try again");
    }

    @Override
    public User updatePassword(User user, String password) {
        user.password = _passwordEncoder.encode(password);

        return _userRepository.save(user);
    }

    @Override
    public UserResult register(RegisterViewModel model) {
        if (model.firstName == "" || model.lastName == "" || model.email == "" || model.password == "" || model.confirmPassword == "")
            return UserResult.failed("All fields are required");

        if (!model.password.equals(model.confirmPassword))
            return UserResult.failed("The passwords does not match");

        User user = new User();

        user.firstName = model.firstName;
        user.lastName = model.lastName;
        user.email = model.email;
        user.password = _passwordEncoder.encode(model.password);
        user.activated = false;
        
        user = _userRepository.save(user);

        String token = _verificationTokenService.generate(user).token;

        try {
            _emailService.send(user, "Account Activation",
                    "<p>We are so glad that you are joining us!<br />" +
                            "Your verification token is: " + token + "<br />" +
                            "Please click on the following link to finish your registration:<br />" +
                            "<a href=\"http://localhost:8080/activate/" + token + "\">" + "http://localhost:8080/activate/" + token + "</a><br />" +
                            "or visit <a href=\"http://localhost:8080/activate\">http://localhost:8080/activate</a> and enter your verification code.</p>");
        } catch (Exception e) {
            return UserResult.failed("Verification token could not be sent");
        }

        return UserResult.success("Verification token has been sent to your mail address");
    }

    @Override
    public PasswordResult changePassword(User user, String currentPassword, String newPassword, String confirmNewPassword) {
        if (!_passwordEncoder.matches(currentPassword, user.password))
            return PasswordResult.failed("Wrong password");

        if (!newPassword.equals(confirmNewPassword))
            return PasswordResult.failed("New passwords does not match");

        User updated = updatePassword(user, newPassword);

        return updated != null ? PasswordResult.success("Password updated successfully") : PasswordResult.failed("Couldn't update password");
    }

    @Override
    public List<User> findByRole(Role role) {
        return find(u -> u.role == role);
    }

    @Override
    public List<User> find(Predicate<User> predicate) {
        return _userRepository.findAll().stream().filter(predicate).collect(Collectors.toList());
    }
}
