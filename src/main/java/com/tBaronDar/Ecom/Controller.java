package com.tBaronDar.Ecom;

import com.tBaronDar.Ecom.model.Product;
import com.tBaronDar.Ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class Controller {
    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello!!!";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() { // We wrap the reply with this object to access the status
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Optional<Product>> getOneProduct(@PathVariable int id) {
        var product = productService.getProductById(id);

        if (product.isPresent()) {
            return new ResponseEntity<>(product, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product savedProduct = productService.addOrUpdateProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProdImage(@PathVariable int id){
        var product = productService.getProductById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get().getImageData(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            Product updatedProduct = productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>("product updated", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        var product = productService.getProductById(id);
        if (product.isPresent()) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
