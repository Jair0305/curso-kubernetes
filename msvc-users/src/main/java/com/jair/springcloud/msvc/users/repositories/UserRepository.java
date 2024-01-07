package com.jair.springcloud.msvc.users.repositories;

import com.jair.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
