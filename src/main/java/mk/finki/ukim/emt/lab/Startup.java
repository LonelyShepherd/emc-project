package mk.finki.ukim.emt.lab;

import mk.finki.ukim.emt.lab.consts.Role;
import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Startup implements CommandLineRunner {

    @Value("${admin.firstname}")
    private String firstName;
    @Value("${admin.lastname}")
    private String lastName;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;

    private final IUserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;

    public Startup(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String...args) {
        password = _passwordEncoder.encode(password);

        User admin = new User();
        admin.email = email;
        admin.firstName = firstName;
        admin.lastName = lastName;
        admin.password = password;
        admin.activated = true;
        admin.role = Role.Admin;

        _userRepository.save(admin);
    }
}