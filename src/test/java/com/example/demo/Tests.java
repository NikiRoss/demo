package com.example.demo;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Tests {

    @Autowired
    UserService userService;

    @Test
    public void createUser(){
        userService.createUser("newuser", "newpass", "ROLE_USER");
    }
}
