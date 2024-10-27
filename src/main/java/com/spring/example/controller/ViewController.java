package com.spring.example.controller;

import com.spring.example.service.ProductService;
import com.spring.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "view";
    }

    @GetMapping("/{id}")
    public String viewProductDetail(@PathVariable("id") long productId, Model model) {
        model.addAttribute("product", productService.getProduct(productId));
        return "view_detail";
    }
}
