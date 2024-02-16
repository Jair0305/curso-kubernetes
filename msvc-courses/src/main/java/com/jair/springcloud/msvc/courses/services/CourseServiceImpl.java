package com.jair.springcloud.msvc.courses.services;

import com.jair.springcloud.msvc.courses.clients.UserClientRest;
import com.jair.springcloud.msvc.courses.models.User;
import com.jair.springcloud.msvc.courses.models.entity.Course;
import com.jair.springcloud.msvc.courses.models.entity.CourseUser;
import com.jair.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public void deleteCourseUserByUserId(Long id) {
        courseRepository.deleteCourseUserByUserId(id);
    }

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent())
        {
            User userMsvc = userClientRest.detail(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent())
        {
            User userNewMsvc = userClientRest.create(user);

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userNewMsvc.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userNewMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> unassignUser(User user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent())
        {
            User userMsvc = userClientRest.detail(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> forIdWithUsers(Long id) {
        Optional<Course> o = courseRepository.findById(id);
        if(o.isPresent())
        {
            Course course = o.get();
            if(!course.getCourseUsers().isEmpty())
            {
                List<Long> ids = course.getCourseUsers().stream().map(cu -> cu.getUserId()).
                        toList();
                List<User> users = userClientRest.getUsersPerCourse(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }
}
