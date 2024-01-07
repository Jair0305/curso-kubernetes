package com.jair.springcloud.msvc.users.controllers;

import com.jair.springcloud.msvc.users.models.entity.User;
import com.jair.springcloud.msvc.users.serivces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/users")
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
    public ResponseEntity<?> create(@RequestBody User user){
        User userDb = userService.save(user);
        return ResponseEntity.status(201).body(userDb);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id){
        Optional<User> userDb = userService.forId(id);
        if (userDb.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        userDb.get().setName(user.getName());
        userDb.get().setEmail(user.getEmail());
        userDb.get().setPassword(user.getPassword());
        return ResponseEntity.status(201).body(userService.save(userDb.get()));
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

}
