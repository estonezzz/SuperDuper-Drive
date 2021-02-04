package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// result.html after every add/edit/delete action
@Controller
@RequestMapping("/result")
public class ResultsController {
    @GetMapping
    public String getResult() {
        return "result";
    }
}
