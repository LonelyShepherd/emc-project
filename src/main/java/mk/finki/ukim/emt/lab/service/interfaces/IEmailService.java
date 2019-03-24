package mk.finki.ukim.emt.lab.service.interfaces;

import mk.finki.ukim.emt.lab.models.entities.User;

import javax.mail.internet.MimeMessage;

public interface IEmailService {
    void send(User user, String verificationToken) throws Exception;
}
