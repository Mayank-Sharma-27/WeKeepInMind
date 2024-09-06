package com.wekeepinmid.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/welcome")
    public String getUser(){
        return  "Mayank";
    }
}
