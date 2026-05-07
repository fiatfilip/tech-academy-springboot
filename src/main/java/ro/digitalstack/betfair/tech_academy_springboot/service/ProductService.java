package ro.digitalstack.betfair.tech_academy_springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Product;
import ro.digitalstack.betfair.tech_academy_springboot.repository.ProductRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class ProductService {
    // @Autowired
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product findById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }
    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    public Collection<Product> findAll() {
        return productRepository.findAll();
    }
}
