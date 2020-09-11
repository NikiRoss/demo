package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.AuthoritiesDao;
import com.example.demo.repository.UserDao;
import com.example.demo.security.CustomSecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthoritiesDao authoritiesDao;


    public void save(User user) {
        userDao.save(user);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    public void enableUser(String username) {
        User user = findByUsername(username);
        System.out.println(">>>>>>>>>>>>enableUser" + user.isEnabled());
        user.setEnabled(true);
        userDao.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username){
        System.out.println(">>> inside loadByUsername");

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User user = userDao.findByUsername(username);

        try {
            if (user == null)

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword().toLowerCase(),
                    user.isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    user.getUserAuthorities());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(">>> user log in OK");
        System.out.println(">>> userDetails:  " + user.getUsername() + user.getPassword());
        return new CustomSecurityUser(user);
    }

}

