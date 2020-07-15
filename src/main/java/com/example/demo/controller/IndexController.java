package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String indexRedirect() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }


    @RequestMapping(value = "/userHome", method = RequestMethod.POST)
    public String loginPost(@ModelAttribute User user, Model model, HttpSession session) {
        User newUser = userService.findByUsername(user.getUsername());
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        model.addAttribute("newUser", newUser);
        System.out.println("newUser------>"+newUser);
        System.out.println("user>>>>>>>>>>>>>>>>>>>>>>>"+user);

            return "userHome";
        }

}
