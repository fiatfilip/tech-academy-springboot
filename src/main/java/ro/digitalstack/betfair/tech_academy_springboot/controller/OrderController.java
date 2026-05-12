package ro.digitalstack.betfair.tech_academy_springboot.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Order;
import ro.digitalstack.betfair.tech_academy_springboot.entity.OrderItem;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Product;
import ro.digitalstack.betfair.tech_academy_springboot.payment.PaymentConfig;
import ro.digitalstack.betfair.tech_academy_springboot.payment.PaymentProcessor;
import ro.digitalstack.betfair.tech_academy_springboot.service.OrderService;
import ro.digitalstack.betfair.tech_academy_springboot.service.ProductService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentProcessor paymentProcessorIngenico;
    private final PaymentProcessor paymentProcessorPayPal;

    public OrderController(OrderService orderService,
                           ProductService productService,
                           @Qualifier("payPalProcessor") PaymentProcessor paymentProcessorPayPal,
                           @Qualifier("ingenicoProcessor") PaymentProcessor paymentProcessorIngenico) {
        this.orderService = orderService;
        this.productService = productService;
        this.paymentProcessorIngenico = paymentProcessorIngenico;
        this.paymentProcessorPayPal = paymentProcessorPayPal;
    }
    @PostMapping("/orders")
    public EntityModel<Order> createOrder(@RequestBody Order order) {
        Order createdOrder =  orderService.createOrder(order);
        return EntityModel.of(createdOrder,//
                linkTo(methodOn(OrderController.class).get(createdOrder.getId())).withSelfRel());
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> getAll() {
        List<EntityModel<Order>> orders= orderService.findAll().stream()
                .map(order -> EntityModel.of(order,//
                        linkTo(methodOn(OrderController.class).get(order.getId())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).getAll()).withRel("orders")))
                .toList();

        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getAll()).withSelfRel());

    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> get(@PathVariable UUID id) {
        Order order = orderService.findById(id);
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).get(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));
    }
    @PostMapping("/orders/{orderId}/items/{productId}")
    public  EntityModel<Order> addItem(@RequestBody OrderItem orderItem,
                                       @PathVariable UUID orderId,
                                       @PathVariable UUID productId) {
        Order order = orderService.findById(orderId);
        Product product = productService.findById(productId);
        orderItem.setProduct(product);
        order.addItem(orderItem);

        order = orderService.update(order);

        System.out.println(order);

        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).get(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));
    }

    @PostMapping("/orders/{id}/pay")
    public EntityModel<Order> pay(@PathVariable UUID id, @RequestBody PaymentConfig paymentConfig) {
        Order order = orderService.findById(id);
        if("ingenico".equalsIgnoreCase(paymentConfig.paymentType)) {
            paymentProcessorIngenico.pay(order);
        } else {
            paymentProcessorPayPal.pay(order);
        }
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).get(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));
    }


}
