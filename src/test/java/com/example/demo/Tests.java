package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.security.Authorities;
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
        User user = new User();
        Authorities authorities = new Authorities();
        user.setUsername("nikiross");
        user.setPassword("password");
        user.setEmail("niki.ross49@gmail.com");
        user.setEnabled(true);
        authorities.setAuthority("ROLE_USER");
        authorities.setUser(user);
        userService.createVerificationToken(user, "newtoken");
        userService.save(user);

        /**
         * need to understand how tokens are created
         */
    }
}
