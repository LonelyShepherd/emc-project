package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.models.entities.VerificationToken;
import mk.finki.ukim.emt.lab.persistance.IUsersRepository;
import mk.finki.ukim.emt.lab.persistance.IVerificationTokenRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IEmailService;
import mk.finki.ukim.emt.lab.service.interfaces.IUsersService;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationTokenService;
import mk.finki.ukim.emt.lab.service.results.UserResult;
import mk.finki.ukim.emt.lab.viewModels.RegisterViewModel;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UsersService implements IUsersService {
    private final IUsersRepository _usersRepository;
    private final IEmailService _emailService;
    private final IVerificationTokenService _verificationTokenService;

    public UsersService(
        IUsersRepository usersRepository,
        IEmailService emailService,
        IVerificationTokenService verificationTokenService) {
        _usersRepository = usersRepository;
        _emailService = emailService;
        _verificationTokenService = verificationTokenService;
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
    public Collection<User> listAll() {
        return _usersRepository.findAll();
    }

    private void sendVerificationEmail(User user) throws Exception {
        VerificationToken token = _verificationTokenService.generate(user);

        try {
            _emailService.send(token.user, token.token);
        } catch (Exception e) {
            throw new RuntimeException("Verification token could not be sent");
        }
    }
}
