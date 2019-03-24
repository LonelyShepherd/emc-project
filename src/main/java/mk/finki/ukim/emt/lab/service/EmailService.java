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
    public void send(User user, String verificationToken) throws Exception {
        MimeMessage message = _sender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setTo(user.email);
        messageHelper.setSubject("Account Activation");
        messageHelper.setText(
            "<p>We are so glad that you are joining us!<br />" +
            "Your verification token is: " + verificationToken + "<br />" +
            "Please click on the following link to finish your registration:<br />" +
            "<a href=\"http://localhost:8080/activate/" + verificationToken + "\">" + "http://localhost:8080/activate/" + verificationToken + "</a><br />" +
            "or visit <a href=\"http://localhost:8080/activate\">http://localhost:8080/activate</a> and enter your verification code.</p>"
        , true);

        _sender.send(message);
    }
}
