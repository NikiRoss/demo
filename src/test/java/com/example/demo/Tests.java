package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

    @Autowired
    private UserService userService;


    @Test
    public void create_A_User(){
        User u = new User();
        u.setEmail("niki@email.com");
        u.setUsername("NikiR84");
        u.setPassword("PASSword238");
        u.setFirstName("Niki");
        u.setLastName("Ross");
        u.setPhone("0777777777");
        u.setEnabled(true);
        userService.createUser(u);

    }

}
