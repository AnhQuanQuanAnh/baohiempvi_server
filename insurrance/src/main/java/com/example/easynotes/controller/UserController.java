package com.example.easynotes.controller;

import com.example.easynotes.model.MailRequest;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.UserRepository;
import com.example.easynotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/login/{name}/{password}")
    public ResponseEntity<?> login(@PathVariable("name") String name, @PathVariable("password") String password) {
        User user = userService.findUser(name,password);
        if (user != null ) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/users/sendingEmail")
    public ResponseEntity<?> sendEmail(@RequestBody MailRequest request) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.user","ngocthuong2561992@gmail.com");
        props.put("mail.smtp.password","Ronaldo256");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication  getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "ngocthuong2561992@gmail.com", "Ronaldo256");
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
