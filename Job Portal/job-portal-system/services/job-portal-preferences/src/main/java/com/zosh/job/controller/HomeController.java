package com.zosh.job.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/home")
public class HomeController {
    @GetMapping
    public String Home(){

        return "hello from job preferences";
    }
}
