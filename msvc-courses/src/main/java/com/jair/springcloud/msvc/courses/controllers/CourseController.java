package com.jair.springcloud.msvc.courses.controllers;

import com.jair.springcloud.msvc.courses.models.User;
import com.jair.springcloud.msvc.courses.models.entity.Course;
import com.jair.springcloud.msvc.courses.services.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Optional<Course> o = courseService.forIdWithUsers(id);          //courseService.forId(id);
        if(o.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(o.get());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result){

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

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId)
    {
        Optional<User> o;

        try{
            o = courseService.assignUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found " + e.getMessage()));
        }
        if(o.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId)
    {
        Optional<User> o;
        try{
            o = courseService.createUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User canot be created or communication error " + e.getMessage()));
        }
        if(o.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/unassign-user/{courseId}")
    public ResponseEntity<?> unassignUser(@RequestBody User user, @PathVariable Long courseId)
    {
        Optional<User> o;

        try{
            o = courseService.unassignUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found " + e.getMessage()));
        }
        if(o.isPresent())
        {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteCourseUserByUserId(@PathVariable Long id)
    {
        courseService.deleteCourseUserByUserId(id);
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
