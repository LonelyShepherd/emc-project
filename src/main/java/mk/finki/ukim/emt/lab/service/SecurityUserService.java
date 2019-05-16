package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.SecurityUser;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService implements UserDetailsService {
    private final IUserRepository _userRepository;

    public SecurityUserService(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return _userRepository
                .findByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(() ->
                    new UsernameNotFoundException("No user with that email was found."));
    }
}
