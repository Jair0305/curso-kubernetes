package com.jair.springcloud.msvc.users.controllers;

import com.jair.springcloud.msvc.users.models.entity.User;
import com.jair.springcloud.msvc.users.serivces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list(){
        return userService.findAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        Optional<User> user = userService.forId(id);
        if (user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        
        if(result.hasErrors()){
            return validate(result);
        }

        if(!user.getEmail().isEmpty() && userService.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("message", "Email already exists"));
        }

        User userDb = userService.save(user);
        return ResponseEntity.status(201).body(userDb);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }
        Optional<User> userDb = userService.forId(id);
        if(userDb.isPresent())
        {
            User userDb2 = userDb.get();
            if(!user.getEmail().equalsIgnoreCase(userDb2.getEmail()) && userService.findByEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("message", "Email already exists"));
            }
            userDb.get().setName(user.getName());
            userDb.get().setEmail(user.getEmail());
            userDb.get().setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userDb = userService.forId(id);
        if (userDb.isPresent()){
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping ("/users-per-course")
    public ResponseEntity getUsersPerCourse(@RequestParam List<Long> ids)
    {
        return ResponseEntity.ok(userService.listUsersByIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
