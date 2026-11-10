package com.devcaleb.ead.authUser.repositories;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.entities.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    boolean existsByUserAndCourseId(User user, UUID courseId);

    @Query(value = "select * from tb_user_course where user_id = :userId", nativeQuery = true)
    List<UserCourse> findAllUserCourseIntoUser(@Param("userId") UUID userId);
}
