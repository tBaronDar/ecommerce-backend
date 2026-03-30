package com.tBaronDar.Ecom.service;

import com.tBaronDar.Ecom.model.Product;
import com.tBaronDar.Ecom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo pr;

    public List<Product> getAllProducts() {
        return pr.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return pr.findById(id);
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return pr.save(product);
    }
}
