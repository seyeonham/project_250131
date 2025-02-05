package com.project_250131.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

    @GetMapping("/sign-in")
    public String signIn() {
        return "user/signIn";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "user/signUp";
    }
}
