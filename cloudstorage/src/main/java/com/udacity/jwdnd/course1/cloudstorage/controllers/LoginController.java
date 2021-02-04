package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// handling /login and logout calls
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

// call from Logout button
    @PostMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }

    // call from browser directly
    @GetMapping("/logout")
    public String logoutView(){
        return "redirect:/login?logout";
    }
}
