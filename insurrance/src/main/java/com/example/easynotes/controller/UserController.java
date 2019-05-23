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
@CrossOrigin(origins = "http://localhost:3005")
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

}
