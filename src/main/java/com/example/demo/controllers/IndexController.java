package com.example.demo.controllers;

import com.example.demo.dao.AuthoritiesDao;
import com.example.demo.events.OnRegistrationCompleteEvent;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.security.Authorities;
import com.example.demo.service.UserService;
import com.example.demo.service.VerificationTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Controller
@Secured("ROLE_USER")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    VerificationTokenServiceImpl tokenService;

    @Qualifier("messageSource")
    @Autowired
    MessageSource messages;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthoritiesDao authoritiesDao;

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

    @GetMapping("/registration")
    public String getSignUp(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("message", "Please sign up!");
        return "registration";
    }

    @PostMapping("signup/user/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") User user, BindingResult result, HttpServletRequest request) {
        System.out.println("IN HERE!");
        try {
            User registered = new User();
            registered.setUsername(user.getUsername());
            registered.setEmail("niki.ross49@gmail.com");
            registered.setPassword(passwordEncoder.encode("password1"));
            registered.setEnabled(false);
            Set<Authorities> authoritiesSet = new HashSet<>();
            Authorities authority = new Authorities();
            authority.setUser(registered);
            authority.setAuthority("ROLE_ADMIN");
            authoritiesSet.add(authority);
            registered.setUserAuthorities(authoritiesSet);
            userService.save(registered);
            authoritiesDao.save(authority);
            String appUrl = request.getRequestURL().toString();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                    request.getLocale(), appUrl));
        } catch (RuntimeException ex) {
            return new ModelAndView("error", "user", user);
        }
        return new ModelAndView("successRegister", "user", user);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiry().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/signup/user/registration/registrationConfirm/{token}")
    public String verificationSuccess(@PathVariable String token){
        VerificationToken dbToken = tokenService.findByToken(token);
        User user = dbToken.getUser();
        //Remove if doesn't work
        user.setEnabled(true);
        //User dbUser = userService.findByUsername(user.getUsername());
        //dbUser.setEnabled(true);
        userService.save(user);
        return "successRegister";
    }

}
