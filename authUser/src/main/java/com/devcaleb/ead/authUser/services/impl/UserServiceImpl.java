package com.devcaleb.ead.authUser.services.impl;

import com.devcaleb.ead.authUser.repositories.UserRepository;
import com.devcaleb.ead.authUser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
}
