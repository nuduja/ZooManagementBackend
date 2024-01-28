package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.model.User;
import com.fulltack.zooManagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public  User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }
}
