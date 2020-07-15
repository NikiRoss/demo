package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByUserId(long userId);
    User findByEmail(String email);
    List<User> findAll();
}
