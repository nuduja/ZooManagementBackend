package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByUsername(String username);

    boolean existsByUsername(String username);

    String deleteByUsername(String username);
}