package com.jair.springcloud.msvc.courses.controllers;

import com.jair.springcloud.msvc.courses.models.entity.Course;
import com.jair.springcloud.msvc.courses.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> create(@Valid Course course, BindingResult result){

        if(result.hasErrors()){
            return validate(result);
        }

        Course courseDb = courseService.save(course);
        return ResponseEntity.ok(courseDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update( @Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }
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

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
