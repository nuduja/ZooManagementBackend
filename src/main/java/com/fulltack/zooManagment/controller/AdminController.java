package com.fulltack.zooManagment.controller;

import com.fulltack.zooManagment.Requests.AdminRequest;
import com.fulltack.zooManagment.auth.JwtService;
import com.fulltack.zooManagment.exception.AdminNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Admin;
import com.fulltack.zooManagment.model.LoginDTO;
import com.fulltack.zooManagment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
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
        return ResponseEntity.ok(service.getAllAdmins());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminByAdminId(@PathVariable String adminId) {
        try {
            return ResponseEntity.ok(service.getAdminByAdminId(adminId));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAdminByUsername/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(service.getAdminByUsername(username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addAdmin(@RequestBody AdminRequest adminRequest) {
        try {
            return ResponseEntity.ok(service.addAdmin(adminRequest));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(service.login(loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(loginDTO.getUsername()));
        } else {
            throw new UsernameNotFoundException("invalid admin request !");
        }
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdminByAdminId(@PathVariable String adminId) {
        try {
            return ResponseEntity.ok(service.deleteAdminByAdminId(adminId));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/searchAdmin")
    public ResponseEntity<List<Admin>> searchAdmin(
            @RequestParam(required = false) String adminId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String username) {
        try {
            return ResponseEntity.ok(service.searchAdmins(adminId, name, username));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updatebyadminid/{adminId}")
    public String updateAdminByAdminId(@PathVariable String adminId, @RequestBody Map<String, Object> updates) {
        service.updateAdminByAdminId(adminId, updates);
        return "Admin updated successfully";
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> ticketsReport() {
        ByteArrayInputStream bis = service.generateAdminsPDF();

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=admins.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/check-admin-username")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = service.checkUsernameExists(username);
        return ResponseEntity.ok(exists);
    }
}