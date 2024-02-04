package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.model.LoginDTO;
import com.fulltack.zooManagment.model.User;
import com.fulltack.zooManagment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user){
        return service.addUser(user);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginDTO loginDTO){
        return service.login(loginDTO.getUsername(), loginDTO.getPassword());
    }

    @PutMapping
    public String updateUser(@RequestBody User user){
        return service.updateUser(user);
    }
}