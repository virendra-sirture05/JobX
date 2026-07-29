package com.project.referral.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
public class HomeController {

    @PostConstruct
    public void init() {
        System.out.println(">>> HomeController Loaded <<<");
    }

    @GetMapping
    public String home() {
        return "hello from job preferences";
    }
}