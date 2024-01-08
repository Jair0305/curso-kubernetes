package com.jair.springcloud.msvc.courses.controllers;

import com.jair.springcloud.msvc.courses.models.entity.Course;
import com.jair.springcloud.msvc.courses.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> list(){
        return ResponseEntity.ok(courseService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        Optional<Course> o = courseService.forId(id);
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }

    @PostMapping
    public ResponseEntity<?> create(Course course){
        Course courseDb = courseService.save(course);
        return ResponseEntity.ok(courseDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Course course){
        Optional<Course> o = courseService.forId(id);
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Course courseDb = o.get();
        courseDb.setName(course.getName());
        return ResponseEntity.ok(courseService.save(courseDb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
