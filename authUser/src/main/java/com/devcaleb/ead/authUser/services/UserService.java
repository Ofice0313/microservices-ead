package com.devcaleb.ead.authUser.services;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    
    List<User> findAll(Pageable pageable);

    Optional<User> findById(UUID userId);

    void delete(User user);

    void save(User user);

    boolean existsByUsername(String username);


    boolean existsByEmail(String email);

    Page<User> findAll(Specification<User> specification, Pageable pageable);
}
