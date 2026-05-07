package ro.digitalstack.betfair.tech_academy_springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Order;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Product;
import ro.digitalstack.betfair.tech_academy_springboot.repository.OrderRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class OrderService {
    // @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Collection<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order update(Order order){
        return orderRepository.save(order);
    }
}
