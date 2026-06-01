package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.repositories.ModuleRepository;
import com.devcaleb.ead.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;
}
