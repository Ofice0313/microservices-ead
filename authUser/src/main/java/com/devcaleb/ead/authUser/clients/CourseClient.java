package com.devcaleb.ead.authUser.clients;

import com.devcaleb.ead.authUser.dto.CourseDTO;
import com.devcaleb.ead.authUser.dto.ResponsePageDTO;
import com.devcaleb.ead.authUser.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.course}")
    String REQUEST_URL_COURSE;

    public Page<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {
        List<CourseDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<CourseDTO>> result = null;
        String url = REQUEST_URL_COURSE + utilsService.createUrl(userId, pageable);
        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            if (result.getBody() != null) {
                searchResult = result.getBody().getContent();
                log.debug("Response Number of elements: {} ", searchResult.size());
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses userId {} ", userId, e);
        }
        log.info("Ending request /courses userId {} ", userId);
        if (result == null || result.getBody() == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return result.getBody();
    }
}
