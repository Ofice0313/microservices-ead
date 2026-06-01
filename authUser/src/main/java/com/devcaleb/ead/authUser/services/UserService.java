package com.devcaleb.ead.authUser.services;

import com.devcaleb.ead.authUser.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    
    List<User> findAll();

    Optional<User> findById(UUID userId);

    void delete(User user);

    void save(User user);

    boolean existsByUsername(String username);


    boolean existsByEmail(String email);
}
