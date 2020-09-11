package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {


    @Query("select * from User where username = :username")
    User findByUsername(String username);
}
