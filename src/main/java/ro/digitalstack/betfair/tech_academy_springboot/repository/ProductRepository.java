package ro.digitalstack.betfair.tech_academy_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
