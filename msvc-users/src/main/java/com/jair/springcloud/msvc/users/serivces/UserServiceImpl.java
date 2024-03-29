package com.jair.springcloud.msvc.users.serivces;

import com.jair.springcloud.msvc.users.clients.CourseClientRest;
import com.jair.springcloud.msvc.users.models.entity.User;
import com.jair.springcloud.msvc.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseClientRest courseClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> forId(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
        courseClientRest.deleteCourseUserByUserId(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.forEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> listUsersByIds(Iterable<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }
}
