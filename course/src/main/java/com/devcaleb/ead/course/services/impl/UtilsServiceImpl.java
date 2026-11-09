package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

//    @Value("${ead.api.url.authuser}")
//    String REQUEST_URI;

    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
        return "/api/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replace(": ", ",");
    }
}
