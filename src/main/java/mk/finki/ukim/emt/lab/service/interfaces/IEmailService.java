package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.User;

public interface IEmailService {
    void send(User user, String subject, String message) throws Exception;
}
