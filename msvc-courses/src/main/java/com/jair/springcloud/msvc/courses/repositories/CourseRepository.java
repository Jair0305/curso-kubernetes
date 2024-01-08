package com.jair.springcloud.msvc.courses.repositories;

import com.jair.springcloud.msvc.courses.models.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {


}
