package com.devcaleb.ead.authUser.specifications;

import com.devcaleb.ead.authUser.entities.User;
import com.devcaleb.ead.authUser.entities.UserCourse;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class)

    })
    public interface UserSpec extends Specification<User> {}

    public static Specification<User> userCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<User, UserCourse> userCourse = root.join("userCourses");
            return criteriaBuilder.equal(userCourse.get("courseId"), courseId);
        };
    }
}
