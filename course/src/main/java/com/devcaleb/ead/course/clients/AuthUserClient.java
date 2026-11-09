package com.devcaleb.ead.course.clients;

import com.devcaleb.ead.course.dto.CourseUserDTO;
import com.devcaleb.ead.course.dto.ResponsePageDTO;
import com.devcaleb.ead.course.dto.UserDTO;
import com.devcaleb.ead.course.services.UtilsService;
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
public class AuthUserClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.authuser}")
    String REQUEST_URL_AUTH_USER;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<UserDTO>> result = null;
        String url = REQUEST_URL_AUTH_USER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            if (result.getBody() != null) {
                searchResult = result.getBody().getContent();
                log.debug("Response Number of elements: {} ", searchResult.size());
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error request /users courseId {} ", courseId, e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        if (result == null || result.getBody() == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return result.getBody();
    }

    public ResponseEntity<UserDTO> getOneUserById(UUID userId) {
        String url = REQUEST_URL_AUTH_USER + "/api/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = REQUEST_URL_AUTH_USER + "/api/users/" + userId + "/courses/subscription";
        var courseUserDTO = new CourseUserDTO();
        courseUserDTO.setCourseId(courseId);
        courseUserDTO.setUserId(userId);
        restTemplate.postForObject(url, courseUserDTO, String.class);
    }
}
