package com.jair.springcloud.msvc.courses.repositories;

import com.jair.springcloud.msvc.courses.models.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("DELETE FROM CourseUser cu WHERE cu.userId = ?1")
    void deleteCourseUserByUserId(Long id);
}
