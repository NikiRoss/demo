package com.example.demo.model;

import com.example.demo.security.Authorities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "username")
    private String username;

    @Column(name ="password")
    private String password;

    @Column(name ="email")
    private String email;

    private boolean enabled;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="user")
    private Set<Authorities> userAuthorities = new HashSet<>();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authorities> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<Authorities> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }
}
