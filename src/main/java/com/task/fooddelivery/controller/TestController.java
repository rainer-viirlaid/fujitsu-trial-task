package com.task.fooddelivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("echo")
    public String echo() {
        return "echo";
    }
}
