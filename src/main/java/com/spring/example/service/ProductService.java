package com.spring.example.service;

import com.spring.example.exception.ProductNotFoundException;
import com.spring.example.model.Product;
import com.spring.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.spring.example.enumeration.ErrorType.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND.getMessage(), id)));
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByUserId(long userId) {
        return getAllProducts().stream()
                .filter(product -> product.getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
