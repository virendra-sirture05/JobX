package com.project.referral.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class HomeController {
    @GetMapping
    public String Home(){
        return "hello from ai service";
    }
}
