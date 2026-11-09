package com.devcaleb.ead.authUser.services.impl;

import com.devcaleb.ead.authUser.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {



    public String createUrl(UUID userId, Pageable pageable) {
        return "/api/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replace(": ", ",");
    }
}
