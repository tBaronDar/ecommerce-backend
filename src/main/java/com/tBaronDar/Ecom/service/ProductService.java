package com.tBaronDar.Ecom.service;

import com.tBaronDar.Ecom.model.Product;
import com.tBaronDar.Ecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo pr;

    public List<Product> getAllProducts() {
        return pr.findAll();
    }
}
