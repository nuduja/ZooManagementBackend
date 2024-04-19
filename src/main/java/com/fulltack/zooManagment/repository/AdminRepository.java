package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByUsername(String username);

    Admin findByAdminId(String adminId);

    boolean existsByAdminId(String adminId);

    boolean existsByUsername(String username);

    String deleteByAdminId(String adminId);

    String deleteByUsername(String username);
}