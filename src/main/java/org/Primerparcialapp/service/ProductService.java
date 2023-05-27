package org.Primerparcialapp.service;

import org.Primerparcialapp.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> create(String bearerToken);

    Product getById(Long id);

    List<Product> getAll();

    Product update(Long id, Product car);

}
