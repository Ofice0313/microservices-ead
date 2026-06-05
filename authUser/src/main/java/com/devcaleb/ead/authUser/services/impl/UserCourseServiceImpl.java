package com.devcaleb.ead.authUser.services.impl;

import com.devcaleb.ead.authUser.repositories.UserCourseRepository;
import com.devcaleb.ead.authUser.services.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;


}
