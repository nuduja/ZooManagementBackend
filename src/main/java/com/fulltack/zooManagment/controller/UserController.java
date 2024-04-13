package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.UserRequest;
import com.fulltack.zooManagment.auth.JwtService;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.exception.TicketNotFoundException;
import com.fulltack.zooManagment.exception.UserNotFoundException;
import com.fulltack.zooManagment.model.Admin;
import com.fulltack.zooManagment.model.LoginDTO;
import com.fulltack.zooManagment.model.User;
import com.fulltack.zooManagment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService service,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        try {
            return ResponseEntity.ok(service.getUserByUsername(username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest){
        try {
            return ResponseEntity.ok(service.addUser(userRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //API Call not in use
    @PostMapping("/login")
    public boolean login(@RequestBody LoginDTO loginDTO){
        return service.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
//    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authentication.isAuthenticated()) {
//            Map<String, String> response = new HashMap<>();
//            response.put("token", jwtService.generateToken(loginDTO.getUsername()));
//            return ResponseEntity.ok(response);
            return ResponseEntity.ok(jwtService.generateToken(loginDTO.getUsername()));
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


    @PutMapping
    public String updateUser(@RequestBody User user){
        return service.updateUser(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username){
        try {
            return ResponseEntity.ok(service.deleteUserByUsername(username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/searchUser")
    public ResponseEntity<List<User>> searchTickets(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username) {
        try {
            return ResponseEntity.ok(service.searchUser(userId, name, username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}