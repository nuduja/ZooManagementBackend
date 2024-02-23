package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.auth.JwtService;
import com.fulltack.zooManagment.model.Admin;
import com.fulltack.zooManagment.model.LoginDTO;
import com.fulltack.zooManagment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminController(AdminService service,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return service.getAllAdmins();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Admin> getUser(@PathVariable String username) {
        return service.getAdmin(username);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        return service.addAdmin(admin);
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
            throw new UsernameNotFoundException("invalid admin request !");
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String username) {
        return service.deleteAdmin(username);
    }
}