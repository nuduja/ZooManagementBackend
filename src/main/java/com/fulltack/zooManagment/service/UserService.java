package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.User;
import com.fulltack.zooManagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<User>> getAllUsers(){
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception e) {
            System.out.println("Error occurred while fetching users" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    public ResponseEntity<User> getUser(String username){
        try{
            return ResponseEntity.ok(repository.findByUsername(username));
        } catch (Exception e){
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try{
            User userDetail = repository.findByUsername(username);
            return userDetail;
        } catch (Exception e){
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }


    public  ResponseEntity<String> addUser(User user){
        try {
            if(!repository.existsByUsername(user.getUsername().trim())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                repository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body("User " + user.getUsername() + " Saved Successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username " + user.getUsername() + " Already Exists");
            }

        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding a user", e);
        }
    }

    //TODO: Not Updating
    //TODO: Remove Password updaing in update profile and introduce another
    @Transactional
    public String updateUser(User userRequest){
        try {
//            if (repository.existsByUsername(userRequest.getUsername())) {
//                User existingUser = repository.findByUsername(userRequest.getUsername());
//                if(userRequest.getEmail() != null) {
//                    existingUser.setEmail(userRequest.getEmail());
//
//                }
//                if(userRequest.getPassword() != null) {
//                    existingUser.setPassword(userRequest.getPassword());
//                }
//                if(userRequest.getPhone() != null) {
//                    existingUser.setPhone(userRequest.getPhone());
//                }
//                if(userRequest.getName() != null) {
//                    existingUser.setName(userRequest.getName());
//                }
//                return "User " + userRequest.getUsername() + " Updated successfully";
            Optional<User> existingUserOptional = Optional.ofNullable(repository.findByUsername(userRequest.getUsername()));
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();

                Optional.ofNullable(userRequest.getEmail()).ifPresent(existingUser::setEmail);
                Optional.ofNullable(userRequest.getPassword()).ifPresent(existingUser::setPassword);
                Optional.ofNullable(userRequest.getPhone()).ifPresent(existingUser::setPhone);
                Optional.ofNullable(userRequest.getName()).ifPresent(existingUser::setName);

                repository.save(existingUser);
                return "User " + userRequest.getUsername() + " Updated successfully";
            } else {
                return "User " + userRequest.getUsername() + " Does not Exist";
            }
        } catch (Exception e){
            throw new ServiceException("Error occurred while updating a user", e);
        }
    }

    public boolean login(String username, String password){
        try {
            if(repository.existsByUsername(username)) {
                User user = repository.findByUsername(username);
                boolean a = passwordEncoder.matches(password, user.getPassword());
                return user != null && a;
            }
            else{
                return false;
            }
        } catch(Exception e){
            throw new ServiceException("Error occurred while login", e);
        }
    }

    public ResponseEntity<String> deleteUser(String username){
        try {
            if(repository.existsByUsername(username)) {
                repository.deleteByUsername(username);
                return ResponseEntity.ok(username + " User Deleted Successfully");
            }
            else{
                return ResponseEntity.ok(username + " User Does not exists");
            }
        } catch (Exception e){
            throw new ServiceException("Error Occurred while Deleting User", e);
        }
    }
}