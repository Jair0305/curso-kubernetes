package com.jair.springcloud.msvc.courses.services;

import com.jair.springcloud.msvc.courses.clients.UserClientRest;
import com.jair.springcloud.msvc.courses.models.User;
import com.jair.springcloud.msvc.courses.models.entity.Course;
import com.jair.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientRest userClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Course> list() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> forId(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<User> assignUser(User user, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> createUser(User user, Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> unassignUser(User user, Long courseId) {
        return Optional.empty();
    }
}
