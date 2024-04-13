package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.TicketRequest;
import com.fulltack.zooManagment.Requests.UserRequest;
import com.fulltack.zooManagment.enums.TicketStatus;
import com.fulltack.zooManagment.enums.TicketType;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.exception.UserNotFoundException;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.model.User;
import com.fulltack.zooManagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    public User convertToUser(UserRequest userRequest) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString().split("-")[0]);
        user.setUsername(userRequest.getUsername());
        user.setName(userRequest.getName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        return user;
    }

    public List<User> getAllUsers() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Users", e);
        }
    }

    public User getUserByUsername(String username) {
        try {
            User user = repository.findByUsername(username);

            if (user == null) {
                throw new UserNotFoundException("User with username " + username + " not found");
            }
            return user;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }

    //Redundant
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            User userDetail = repository.findByUsername(username);
            return userDetail;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }


    public User addUser(UserRequest userRequest) {
        try {
            User user = convertToUser(userRequest);
            if (!repository.existsByUsername(user.getUsername().trim())) {
                if (user.getUsername() == null || user.getPassword() == null) {
                    throw new IllegalArgumentException("Username and Password must be valid.");
                }
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding a user", e);
        }
    }

    //TODO: Not Updating
    //TODO: Remove Password updaing in update profile and introduce another
    @Transactional
    public String updateUser(User userRequest) {
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
        } catch (Exception e) {
            throw new ServiceException("Error occurred while updating a user", e);
        }
    }

    public boolean login(String username, String password) {
        try {
            if (repository.existsByUsername(username)) {
                User user = repository.findByUsername(username);
                boolean a = passwordEncoder.matches(password, user.getPassword());
                return user != null && a;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException("Error occurred while login", e);
        }
    }

    public String deleteUserByUsername(String username) {
        try {
            if (repository.existsByUsername(username)) {
                repository.deleteByUsername(username);
                return "User Deleted Successfully";
            } else {
                return "User doesn't exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting User", e);
        }
    }

    public List<User> searchUser(String userId, String name, String username) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (userId != null && !userId.isEmpty()) {
                criteria.add(Criteria.where("userId").regex(userId, "i")); // case-insensitive search
            }
            if (name != null && !name.isEmpty()) {
                criteria.add(Criteria.where("name").is(name));
            }
            if (username != null && !username.isEmpty()) {
                criteria.add(Criteria.where("username").is(username));
            }

            if (!criteria.isEmpty()) {
                query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
            }

            return mongoTemplate.find(query, User.class);
        } catch(Exception e){
            throw new ServiceException("Error Searching User", e);
        }
    }
}