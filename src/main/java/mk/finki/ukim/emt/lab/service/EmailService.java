package mk.finki.ukim.emt.lab.service;

import mk.finki.ukim.emt.lab.models.entities.User;
import mk.finki.ukim.emt.lab.service.interfaces.IEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {
    private final JavaMailSender _sender;

    public EmailService(JavaMailSender sender) {
        _sender = sender;
    }

    @Override
    public void send(User user, String subject, String message) throws Exception {
        MimeMessage mimeMessage = _sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setTo(user.email);
        messageHelper.setSubject(subject);
        messageHelper.setText(message, true);

        _sender.send(mimeMessage);
    }
}
