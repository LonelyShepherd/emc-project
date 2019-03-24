package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.models.entities.VerificationToken;

public interface IVerificationTokenService {
    VerificationToken generate(User user);
}
