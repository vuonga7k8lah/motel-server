package com.vuongkma.motel.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hello")
public class AuthController {
    @GetMapping()
    private void hello(){
        System.out.println("hello");
    }

}
