package org.Primerparcialapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.Primerparcialapp.model.Product;
import org.Primerparcialapp.model.User;
import org.Primerparcialapp.repository.ProductRepository;
import org.Primerparcialapp.repository.UserRepository;
import org.Primerparcialapp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl extends AbstractClient  implements ProductService {

    protected ProductServiceImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public List<Product> create(String bearerToken) {
        // Validar el token de autorización
        // Extraer el token de autorización sin el prefijo "Bearer "
        String token = bearerToken.substring(7);
        if (bearerToken != null) {
            if (StringUtils.hasText(token) && !validateToken(token)) {
            throw new RuntimeException("Token de autorización inválido");
        }
        }
        String uri = baseUrl + "/products";
        if(getAll().size()==0) {
            try {
                ResponseEntity<List<Product>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){});
                if (response.getStatusCode().is2xxSuccessful()) {
                    List<Product> products = Objects.requireNonNull(response.getBody());
                    productRepository.saveAll(products);
                    return products;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            //log.error("Error in user creation - httpStatus was: {}", response.getStatusCode());
            throw new RuntimeException("Error");
        }else
        {
            // Obtener el usuario correspondiente al token de autorización
            Optional<User> user = getUserFromToken(bearerToken);

            // Crear un nuevo producto y establecer el usuario como propietario
            Product newProduct = new Product();
            newProduct.setUsuario(user.get());
            // Establecer los demás atributos del producto si es necesario
            productRepository.save(newProduct);

            return getAll();
        }
    }

/*
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
 */
    private boolean validateToken(String bearerToken) {
        return jwtUtil.validateToken(bearerToken);
    }

    private Optional<User> getUserFromToken(String bearerToken) {

        String token = bearerToken.substring(7);

        String secretKey = jwtUtil.getKey(token);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            Optional<User> user = userRepository.findByMail(username);

            // Validar cualquier otro criterio si es necesario
            if (user == null) {
                throw new RuntimeException("Usuario no encontrado");
            }

            // Retornar el objeto User asociado al token
            return user;
        } catch (Exception e) {
            // Ocurrió una excepción al verificar o analizar el token
            throw new RuntimeException("Token JWT inválido o expirado");
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
