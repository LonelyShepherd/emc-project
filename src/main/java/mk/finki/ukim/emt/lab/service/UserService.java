package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.models.entities.VerificationToken;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IEmailService;
import mk.finki.ukim.emt.lab.service.interfaces.IUserService;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationTokenService;
import mk.finki.ukim.emt.lab.service.results.PasswordResult;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final IEmailService _emailService;
    private final PasswordEncoder _passwordEncoder;
    private final IUserRepository _usersRepository;
    private final IVerificationTokenService _verificationTokenService;

    public UserService(
        IEmailService emailService,
        PasswordEncoder passwordEncoder,
        IUserRepository usersRepository,
        IVerificationTokenService verificationTokenService) {
        _emailService = emailService;
        _passwordEncoder = passwordEncoder;
        _usersRepository = usersRepository;
        _verificationTokenService = verificationTokenService;
    }

    @Override
    public User update(User user) {
        return _usersRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return _usersRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserResult create(RegisterViewModel user) throws Exception {
        if (user.firstName == "" || user.lastName == "" || user.email == "" || user.password == "" || user.confirmPassword == "")
            return  UserResult.failed("All fields are required");

        if (!user.password.equals(user.confirmPassword))
            return UserResult.failed("The passwords does not match");

        User u = new User();

        u.firstName = user.firstName;
        u.lastName = user.lastName;
        u.email = user.email;
        u.password = user.password;
        u.activated = false;

        sendVerificationEmail(_usersRepository.save(u));

        return UserResult.success("Authentication token has been sent to your mail address");
    }

    @Override
    public PasswordResult changePassword(User user, String currentPassword, String newPassword, String confirmNewPassword) {
        if (!_passwordEncoder.matches(currentPassword, user.password))
            return PasswordResult.failed("Wrong password");

        if (!newPassword.equals(confirmNewPassword))
            return PasswordResult.failed("New passwords does not match");

        user.password = _passwordEncoder.encode(newPassword);

        User updated = update(user);

        return updated != null ? PasswordResult.failed("Password updated successfully") : PasswordResult.failed("Couldn't update password");
    }

    private void sendVerificationEmail(User user) {
        VerificationToken token = _verificationTokenService.generate(user);

        try {
            _emailService.send(token.user, token.token);
        } catch (Exception e) {
            throw new RuntimeException("Verification token could not be sent");
        }
    }
}
