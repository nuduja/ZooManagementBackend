package com.fulltack.zooManagment.service;

import com.fulltack.zooManagment.Requests.AdminRequest;
import com.fulltack.zooManagment.exception.AdminNotFoundException;
import com.fulltack.zooManagment.exception.ServiceException;
import com.fulltack.zooManagment.model.Admin;
import com.fulltack.zooManagment.model.Animal;
import com.fulltack.zooManagment.model.Ticket;
import com.fulltack.zooManagment.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Admin convertToAdmin(AdminRequest adminRequest) {
        Admin admin = new Admin();
        admin.setAdminId(UUID.randomUUID().toString().split("-")[0]);
        admin.setName(adminRequest.getName());
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(adminRequest.getPassword());
        admin.setRole(adminRequest.getRole());
        return admin;
    }

    public List<Admin> getAllAdmins() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while fetching all Tickets", e);
        }
    }

    public Admin getAdminByUsername(String username) {
        try {
            Admin admin = repository.findByUsername(username);

            if (admin == null) {
                throw new AdminNotFoundException("Ticket with ID " + username + " not found");
            }
            return admin;
        } catch (Exception e) {
            throw new ServiceException("Error occurred while fetching specific Admin", e);
        }
    }

    public String addAdmin(AdminRequest adminRequest) {
        try {
            Admin admin = convertToAdmin(adminRequest);
            if (admin.getUsername() == null || admin.getPassword() == null) {
                throw new IllegalArgumentException("Admin username and password must be valid.");
            }
            if (!repository.existsByUsername(admin.getUsername().trim())) {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                repository.save(admin);
                return "Admin Created Successful";
            } else {
                return "Admin User already exists";
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

    public String deleteAdmin(String username) {
        try {
            if (repository.existsByUsername(username)) {
                repository.deleteByUsername(username);
                return "Admin Deleted Successfully";
            } else {
                return "Admin doesn't exists";
            }
        } catch (Exception e) {
            throw new ServiceException("Error Occurred while Deleting Admin User", e);
        }
    }

    public List<Admin> searchAdmins(String adminId, String name, String username) {
        try {
            Query query = new Query();
            List<Criteria> criteria = new ArrayList<>();

            if (adminId != null && !adminId.isEmpty()) {
                criteria.add(Criteria.where("adminId").regex(adminId, "i")); // case-insensitive search
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

            return mongoTemplate.find(query, Admin.class);
        } catch (Exception e) {
            throw new ServiceException("Error Searching Ticket", e);
        }
    }

    public void updateAdminByAdminId(String adminId, Map<String, Object> updates) {
        Query query = new Query(Criteria.where("adminId").is(adminId));
        Update update = new Update();
        updates.forEach((key, value) -> {
            if (!key.equals("id") && !key.equals("adminId")) {
                update.set(key, value);
            }
        });
        mongoTemplate.findAndModify(query, update, Admin.class);
    }
}