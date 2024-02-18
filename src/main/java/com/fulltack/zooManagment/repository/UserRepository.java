package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    String deleteByUsername(String username);
}