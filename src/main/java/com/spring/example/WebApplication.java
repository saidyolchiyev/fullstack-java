package com.spring.example;

import com.spring.example.model.Role;
import com.spring.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.spring.example.enumeration.RoleType.ADMIN;
import static com.spring.example.enumeration.RoleType.USER;

@SpringBootApplication
public class WebApplication {

	public final static String path = "C:\\Users\\asus x540\\Desktop\\ProgrammingLanguages\\Spring\\Final\\example\\example\\src\\main\\resources\\static\\img\\";

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(USER.name()));
			userService.saveRole(new Role(ADMIN.name()));
		};
	}

	public static void saveFile(MultipartFile multipartFile) {
		try {
			final String newPath = path + multipartFile.getOriginalFilename();
			multipartFile.transferTo(new File(newPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

