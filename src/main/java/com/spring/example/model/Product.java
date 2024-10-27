package com.spring.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotEmpty(message = "Brand should not be empty")
    private String brand;

    @NotEmpty(message = "Model should not be empty")
    private String model;

    @Min(value = 1, message = "Min value is 1")
    private int RAM;

    @NotEmpty(message = "CPU should not be empty")
    private String CPU;

    @Min(value = 1, message = "Min value is 1")
    private int memory;

    private String memoryType;
    private String conditionType;

    @NotEmpty(message = "Operating System should not be empty")
    private String operatingSystem;

    @NotEmpty(message = "Graphics Card should not be empty")
    private String graphicsCard;

    private String img;

    @Min(value = 1, message = "Min value is 1")
    private int price;

    @NotEmpty(message = "Description should not be empty")
    private String descriptionText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}