package com.jair.springcloud.msvc.courses.services;

import com.jair.springcloud.msvc.courses.models.User;
import com.jair.springcloud.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> list();
    Optional<Course> forId(Long id);
    Course save(Course course);
    void delete(Long id);

    Optional<User> assignUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> unassignUser(User user, Long courseId);


}
