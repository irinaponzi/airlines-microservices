package com.codeki.authservice.controller;

import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.model.Product;
import com.codeki.authservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class AdminController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("/admin/save-product")
    public ResponseEntity<Object> saveProduct(@RequestBody ReqResponse productRequest) {
        Product productToSave = new Product();
        productToSave.setName(productRequest.getName());
        return ResponseEntity.ok(productRepository.save(productToSave));
    }

    @GetMapping("/user/alone")
    public ResponseEntity<Object> userAlone() {
        return ResponseEntity.ok("Users alone can access this API only");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> AdminUserBoth() {
        return ResponseEntity.ok("Both admin and users can access this API");
    }
}
