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

    public  String addUser(User user){
        try {
            if(!repository.existsByUsername(user.getUsername().trim())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                repository.save(user);
                return "User " + user.getUsername() + " Saved Successfully";
            }
            else {
                return "Username " + user.getUsername() + " Already Exists";
            }

        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding a user", e);
        }
    }

    //TODO: Not Updating
    public String updateUser(User userRequest){
        try {
            if (repository.existsByUsername(userRequest.getUsername())) {
                User existingUser = repository.findByUsername(userRequest.getUsername());
                if(userRequest.getEmail() != null) {
                    existingUser.setEmail(userRequest.getEmail());
                }
                if(userRequest.getPassword() != null) {
                    existingUser.setPassword(userRequest.getPassword());
                }
                if(userRequest.getPhone() != null) {
                    existingUser.setPhone(userRequest.getPhone());
                }
                if(userRequest.getName() != null) {
                    existingUser.setName(userRequest.getName());
                }
                return "User " + userRequest.getUsername() + " Updated successfully";
            } else {
                return "User " + userRequest.getUsername() + " Does not Exist";
            }
        } catch (Exception e){
            throw new ServiceException("Error occurred white updating a user", e);
        }
    }

    public boolean login(String username, String password){
        User user = repository.findByUsername(username);
        boolean a = passwordEncoder.matches(password, user.getPassword());
        return user != null && a;
    }
}