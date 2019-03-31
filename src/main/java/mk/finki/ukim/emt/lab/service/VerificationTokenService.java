package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.models.entities.VerificationToken;
import mk.finki.ukim.emt.lab.persistence.IUserRepository;
import mk.finki.ukim.emt.lab.persistence.IVerificationTokenRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationTokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class VerificationTokenService implements IVerificationTokenService {
    private IUserRepository _userRepository;
    private IVerificationTokenRepository _verificationTokenRepository;

    public VerificationTokenService(
        IUserRepository userRepository,
        IVerificationTokenRepository verificationTokenRepository) {
        _userRepository = userRepository;
        _verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken generate(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");

        VerificationToken verificationToken = new VerificationToken();

        verificationToken.token = token;
        verificationToken.user = user;
        verificationToken.expiryDate = verificationToken.calculateExpiryDate();

        return _verificationTokenRepository.save(verificationToken);
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Skopje")
    public void deleteExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        Date now = new Date(calendar.getTime().getTime());

        _verificationTokenRepository
                .deleteByExpiryDateLessThan(now)
                .forEach(token -> {
                    User user = token.user;

                    if (!user.activated)
                        _userRepository.delete(user);
                });
    }
}
