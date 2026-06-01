package com.devcaleb.ead.authUser.services.impl;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.repositories.UserRepository;
import com.devcaleb.ead.authUser.services.UserService;
import com.devcaleb.ead.authUser.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll(Pageable pageable) {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Page<User> findAll(Specification<User> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }


}
