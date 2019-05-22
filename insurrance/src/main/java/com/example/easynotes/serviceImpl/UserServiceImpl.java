package com.example.easynotes.serviceImpl;

import com.example.easynotes.model.User;
import com.example.easynotes.repository.UserRepository;
import com.example.easynotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by ThuongPham on 22/05/2019.
 */
@Service
public class  UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User findUser(String name, String password) {
        return userRepository.findUser(name, password);
    }
}
