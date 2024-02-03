package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
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
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching users", e);
        }
    }

    public  User addUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding a user", e);
        }
    }

    public boolean login(String username, String password){
        User user = repository.findByUsername(username);
        boolean a = passwordEncoder.matches(password, user.getPassword());
        return user != null && a;
    }
}