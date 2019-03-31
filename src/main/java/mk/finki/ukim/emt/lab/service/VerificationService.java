package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import mk.finki.ukim.emt.lab.persistence.IVerificationTokenRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class VerificationService implements IVerificationService {
    private final IUserRepository _userRepository;
    private final IVerificationTokenRepository _verificationTokenRepository;

    public VerificationService(
        IUserRepository userRepository,
        IVerificationTokenRepository verificationTokenRepository) {
        _userRepository = userRepository;
        _verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public boolean verifyUser(String token) {
        User user = getUserByToken(token);

        if (user == null)
            return false;

        user.activated = true;
        _userRepository.save(user);

        return true;
    }

    private User getUserByToken(String token) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        Date now = new Date(calendar.getTime().getTime());

        return _verificationTokenRepository.findByToken(token)
                .map(t -> {
                    Date expires = new Date(t.expiryDate.getTime());

                    return !expires.before(now) ? t.user : null;
                })
                .orElse(null);
    }
}
