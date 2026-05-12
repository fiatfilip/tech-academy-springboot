package ro.digitalstack.betfair.tech_academy_springboot.payment;

import ro.digitalstack.betfair.tech_academy_springboot.entity.Order;

public interface PaymentProcessor {
    boolean pay(Order order);
}
