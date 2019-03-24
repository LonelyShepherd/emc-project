package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.models.entities.VerificationToken;
import mk.finki.ukim.emt.lab.persistence.IVerificationTokenRepository;
import mk.finki.ukim.emt.lab.service.interfaces.IVerificationTokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationTokenService implements IVerificationTokenService {
    private IVerificationTokenRepository _verificationTokenRepository;

    public VerificationTokenService(IVerificationTokenRepository verificationTokenRepository) {
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
}
