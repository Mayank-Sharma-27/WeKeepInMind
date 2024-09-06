package com.wekeepinmind.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/welcome")
    public String getUser(){
        return  "Mayank";
    }
}
