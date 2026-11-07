package com.devcaleb.ead.course.services.impl;

import com.devcaleb.ead.course.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    String REQUEST_URI = "http://localhost:8081";

    public String createUrl(UUID courseId, Pageable pageable) {
        return REQUEST_URI + "/api/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replace(": ", ",");
    }
}
