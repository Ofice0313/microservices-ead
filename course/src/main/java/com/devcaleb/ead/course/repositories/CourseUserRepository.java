package com.devcaleb.ead.course.repositories;

import com.devcaleb.ead.course.entities.Course;
import com.devcaleb.ead.course.entities.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseAndUserId(Course course, UUID userId);

    @Query(value = "select * from tb_course_user where course_id =:courseId", nativeQuery = true)
    List<CourseUser> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);
}
