package ro.digitalstack.betfair.tech_academy_springboot.payment;

import org.springframework.stereotype.Component;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Order;

@Component("payPalProcessor")
public class PayPalProcessor implements PaymentProcessor {
    @Override
    public boolean pay(Order order) {
        // logica de plata
        System.out.println("PayPalProcessor paying order");
        return false;
    }
}
