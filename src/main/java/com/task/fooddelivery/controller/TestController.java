package com.task.fooddelivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * Testing endpoint to check if the application is running and reachable.
     *
     * @return echo
     */
    @GetMapping("echo")
    public String echo() {
        return "echo";
    }
}
