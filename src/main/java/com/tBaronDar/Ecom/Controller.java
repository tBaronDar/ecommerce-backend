package com.tBaronDar.Ecom;

import com.tBaronDar.Ecom.model.Product;
import com.tBaronDar.Ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class Controller {
    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello!!!";
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return productService.getAllProducts();
    }
}
