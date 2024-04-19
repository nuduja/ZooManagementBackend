package com.fulltack.zooManagment.repository;

import com.fulltack.zooManagment.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    User findByUserId(String userId);

    boolean existsByUsername(String username);

    boolean existsByUserId(String userId);

    String deleteByUsername(String username);

    String deleteByUserId(String userId);
}