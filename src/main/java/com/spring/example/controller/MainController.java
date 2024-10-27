package com.spring.example.controller;

import com.spring.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping
    public String index(Authentication authentication, Model model) {
        model.addAttribute("authenticated", authentication == null ? false : true);
        if (authentication != null) model.addAttribute("user", userService.getUser(authentication.getName()));
        return "main";
    }
}
