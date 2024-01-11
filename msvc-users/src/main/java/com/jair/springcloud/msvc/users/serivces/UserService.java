package com.jair.springcloud.msvc.users.serivces;

import com.jair.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> forId(Long id);
    User save(User user);
    void delete(Long id);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
