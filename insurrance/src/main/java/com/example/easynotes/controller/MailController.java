package com.example.easynotes.controller;

import com.example.easynotes.model.MailRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by ThuongPham on 23/05/2019.
 */
@CrossOrigin(origins = "http://localhost:3005")
@RestController
@RequestMapping("/api")
public class MailController {

    @PostMapping("/users/sendingEmail")
    public ResponseEntity<?> sendEmail(@RequestBody MailRequest request) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.user","abcd@gmail.com");
        props.put("mail.smtp.password","123");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "abcd@gmail.com", "123");
                    }
                });
        session.setDebug(false);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(request.getFrom()));
            InternetAddress[] addressTo = new InternetAddress().parse(request.getTo());
            msg.setRecipients(Message.RecipientType.TO, addressTo);


            if (request.getCc() != null && !"".equals(request.getCc())) {
                InternetAddress[] addressCC = new InternetAddress().parse(request.getCc());
                msg.setRecipients(Message.RecipientType.CC, addressCC);
            }
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbpCont = new MimeBodyPart();
            mbpCont.setContent("133334", "text/html; charset=UTF-8");
            mp.addBodyPart(mbpCont);
            msg.setContent(mp);
            msg.setSubject(request.getSubject(), "UTF-8");
            Transport.send(msg);
            System.out.println("fine!!");
        }	catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
