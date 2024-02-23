package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Admin;
import com.fulltack.zooManagment.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception e) {
            System.out.println("Error occurred while fetching admins" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    public ResponseEntity<Admin> getAdmin(String username) {
        try {
            return ResponseEntity.ok(repository.findByUsername(username));
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific user", e);
        }
    }

    public ResponseEntity<String> addAdmin(Admin admin) {
        try {
            if (!repository.existsByUsername(admin.getUsername().trim())) {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                repository.save(admin);
                return ResponseEntity.status(HttpStatus.CREATED).body("User " + admin.getUsername() + " Saved Successfully");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username " + admin.getUsername() + " Already Exists");
            }

        } catch (Exception e) {
            throw new ServiceException("Error occurred while adding an admin", e);
        }
    }

    public boolean login(String username, String password) {
        try {
            if (repository.existsByUsername(username)) {
                Admin admin = repository.findByUsername(username);
                boolean a = passwordEncoder.matches(password, admin.getPassword());
                return admin != null && a;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ServiceException("Error occurred while login", e);
        }
    }

    public ResponseEntity<String> deleteAdmin(String username) {
        try {
            if (repository.existsByUsername(username)) {
                repository.deleteByUsername(username);
                return ResponseEntity.ok(username + " Admin " + username + " Deleted Successfully");
            } else {
                return ResponseEntity.ok(username + " Admin " + username + " Does not exists");
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting User", e);
        }
    }
}