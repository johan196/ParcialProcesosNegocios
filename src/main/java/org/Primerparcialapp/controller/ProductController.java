package org.Primerparcialapp.controller;

import org.Primerparcialapp.model.Product;
import org.Primerparcialapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    private final Map response = new HashMap<>();

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public List<Product> createProducts() {
        return this.productService.create();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductsById(@PathVariable(name = "id") Long id) {
        try {
            return new ResponseEntity<Product>(this.productService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "No se encontro el id proporcionado");
            response.put("data", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> geAllProducts() {
        try {
            return new ResponseEntity<List<Product>>(this.productService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "No se obtuvieron los productos");
            response.put("data", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> createProducts(@RequestBody Product product) {
        try {
            return new ResponseEntity<Product>(this.productService.update(product.getId(), product), HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "No se encontro el id proporcionado");
            response.put("data", e.getMessage());
            return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
