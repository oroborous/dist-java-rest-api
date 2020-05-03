package edu.wctc.rest.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDemoController {
    @GetMapping("/rest")
    public String sayHello() {
        return "Hello world";
    }
}
