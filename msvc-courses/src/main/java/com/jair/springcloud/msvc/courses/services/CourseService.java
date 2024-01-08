package com.jair.springcloud.msvc.courses.services;

import com.jair.springcloud.msvc.courses.entities.Course;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> list();
    Optional<Course> forId(Long id);
    Course save(Course course);
    void delete(Long id);

}
