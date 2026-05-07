package ro.digitalstack.betfair.tech_academy_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
