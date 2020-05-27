package fr.uca.cdr.skillful_network.services.impl;

import java.util.Date;

import fr.uca.cdr.skillful_network.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String NEW_LINE = System.getProperty("line.properties");

    private static final String HEADER_MAIL = String.format("Bonjour, %s Vous trouverez ci-dessous le code permettant de terminer votre inscription.%s", NEW_LINE + NEW_LINE, NEW_LINE + NEW_LINE);

    private static final String FOOTER_MAIL = String.format("%s Cordialement", NEW_LINE + NEW_LINE);

    private static final String SUBJECT_MAIL = "Inscription Réseau Habile";

    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendEmail(String email, String codeAutoGen) {
        String messageText = HEADER_MAIL + codeAutoGen + FOOTER_MAIL;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(SUBJECT_MAIL);
        message.setSentDate(new Date());
        message.setText(messageText);
        emailSender.send(message);
    }
}
