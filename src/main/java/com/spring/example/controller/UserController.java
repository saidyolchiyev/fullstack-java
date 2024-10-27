package com.spring.example.controller;

import com.spring.example.WebApplication;
import com.spring.example.enumeration.ErrorType;
import com.spring.example.model.dto.UserRequest;
import com.spring.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.spring.example.WebApplication.*;
import static com.spring.example.enumeration.ErrorType.USER_ALREADY_EXISTS;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegPage(Model model) {
        model.addAttribute("user", new UserRequest());
        return "reg";
    }

    @PostMapping("/register")
    public String register(@RequestParam("avatarImg") MultipartFile multipartFile,
                           @ModelAttribute("user") @Valid UserRequest userRequest,
                           BindingResult bindingResult) {
        userRequest.setAvatar(multipartFile.getOriginalFilename());
        if (bindingResult.hasErrors()) return "reg";

        if (userService.doesUserExists(userRequest.getUsername())) {
            bindingResult.addError(new FieldError("user", "username",
                    String.format(USER_ALREADY_EXISTS.getMessage(), userRequest.getUsername())));
            return "reg";
        }

        userService.register(userRequest);
        if (!Files.exists(Paths.get(path + multipartFile.getOriginalFilename()))) saveFile(multipartFile);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
