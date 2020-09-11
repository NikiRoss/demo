package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
@Secured("ROLE_USER")
public class IndexController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/")
    public String root() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/home")
    public String success(Model model, Principal principal) {
        System.out.println("home");
        User user = (User) userService.loadUserByUsername(principal.getName());
        System.out.println(user.toString());
        model.addAttribute("user", user);
        return "home";
    }
    @GetMapping("/newtemp")
    public String newTemp(Model model, @ModelAttribute User user, Principal principal) {
        user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        System.out.println(user.toString());
        return "newtemp";
    }

}
