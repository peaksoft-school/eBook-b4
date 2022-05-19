package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor

public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${from}")
    private String from;

    @Value("${subject}")
    private String subject;

    public void send(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        this.javaMailSender.send(simpleMailMessage);
    }
}
