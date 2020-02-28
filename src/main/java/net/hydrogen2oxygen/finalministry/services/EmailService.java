package net.hydrogen2oxygen.finalministry.services;

import net.hydrogen2oxygen.finalministry.services.mail.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.host}")
    private String host;

    public void sendSimpleMessage(Mail mail){

        if ("localhost".equals(host)) {
            logger.info("We are development mode, so no real email service!");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}
