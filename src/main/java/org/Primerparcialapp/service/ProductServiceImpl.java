package org.Primerparcialapp.service;

import lombok.extern.slf4j.Slf4j;
import org.Primerparcialapp.model.Product;
import org.Primerparcialapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductServiceImpl extends AbstractClient  implements ProductService {

    protected ProductServiceImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> create() {
        String uri = baseUrl + "/products";
        if(getAll().size()==0) {
            try {
                ResponseEntity<List<Product>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){});
                if (response.getStatusCode().is2xxSuccessful()) {
                    List<Product> products = Objects.requireNonNull(response.getBody());
                    productRepository.saveAll(products);
                    return products;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //log.error("Error in user creation - httpStatus was: {}", response.getStatusCode());
            throw new RuntimeException("Error");
        }else
        {
            return getAll();
        }
    }

    @Override
    public Product getById(Long id) {
        Product car1 = productRepository.findById(id).get();
        if(car1 != null)
        {
            return car1;
        }
        throw new RuntimeException("Error");
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Long id, Product car) {
        Product car1 = productRepository.findById(id).get();
        car1.setTitle(car.getTitle());
        car1.setPrice(car.getPrice());
        car1.setDescription(car.getDescription());
        car1.setCategory(car.getCategory());
        car1.setImage(car.getImage());
        car1.setRating(car.getRating());
        return productRepository.save(car1);
    }

}
