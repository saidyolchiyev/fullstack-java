package com.spring.example.controller;

import com.spring.example.model.Product;
import com.spring.example.model.User;
import com.spring.example.service.ProductService;
import com.spring.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.spring.example.WebApplication.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public String getProductsPage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("products", productService.getProductsByUserId(user.getId()));
        return "products";
    }

    @GetMapping("/create")
    public String getProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping("/create")
    public String saveProduct(@RequestParam("productImg") MultipartFile multipartFile,
                              @ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult,
                              Authentication authentication) {
        if (bindingResult.hasErrors()) return "product_form";

        product.setUser(userService.getUser(authentication.getName()));
        product.setImg(multipartFile.getOriginalFilename());
        productService.saveProduct(product);
        if (!Files.exists(Paths.get(path + multipartFile.getOriginalFilename()))) saveFile(multipartFile);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "product_edit";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("productImg") MultipartFile multipartFile,
                              @ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "product_edit";

        product.setImg(multipartFile.getOriginalFilename());
        productService.saveProduct(product);
        if (!Files.exists(Paths.get(path + multipartFile.getOriginalFilename()))) saveFile(multipartFile);
        return "redirect:/products";
    }
}
