package com.example.demo.service;

import com.example.demo.dao.AuthoritiesRepo;
import com.example.demo.dao.UserRepo;
import com.example.demo.security.Authorities;
import com.example.demo.security.CustomSecurityUser;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthoritiesRepo authoritiesRepo;

    public User createUser(String username, String password, String role){
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(password));
        Authorities a = new Authorities();
        a.setUser(u);
        a.setAuthority(role);
        userRepo.save(u);
        authoritiesRepo.save(a);
        return u;
    }

    public Iterable<User> findAllUsers(){
        return userRepo.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username and or password was incorrect.");

        return new CustomSecurityUser(user);
    }

}
