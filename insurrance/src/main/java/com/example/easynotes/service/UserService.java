package com.example.easynotes.service;

import com.example.easynotes.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by ThuongPham on 22/05/2019.
 */
@Service
public interface UserService {
    User findUser(String name, String password);
}
