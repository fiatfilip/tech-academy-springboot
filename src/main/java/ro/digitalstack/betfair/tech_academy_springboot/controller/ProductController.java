package ro.digitalstack.betfair.tech_academy_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import ro.digitalstack.betfair.tech_academy_springboot.entity.Product;
import ro.digitalstack.betfair.tech_academy_springboot.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public EntityModel<Product> create(@RequestBody Product product) {
        Product createdProduct =  productService.create(product);
        System.out.println(createdProduct);
        return EntityModel.of(createdProduct,//
                linkTo(methodOn(ProductController.class).get(createdProduct.getId())).withSelfRel());
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> getAll() {
        List<EntityModel<Product>> products= productService.findAll().stream()
                .map(product -> EntityModel.of(product,//
                linkTo(methodOn(ProductController.class).get(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products")))
                .toList();

        return CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getAll()).withSelfRel());

    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> get(@PathVariable UUID id) {
        Product product = productService.findById(id);
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).get(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products"));
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable UUID id) {
        productService.delete(id);
    }

    @PutMapping("/products/{id}")
    public EntityModel<Product> update(@PathVariable UUID id, @RequestBody Product product) {
        Product productInDb = productService.findById(id);
        if(productInDb != null){
            productInDb.setName(product.getName());
            productInDb.setPrice(product.getPrice());
            productService.update(productInDb);
        } else{
            productService.create(product);
        }

        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).get(id)).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products"));
    }


}
