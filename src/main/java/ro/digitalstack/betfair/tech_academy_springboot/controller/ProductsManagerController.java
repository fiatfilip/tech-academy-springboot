package ro.digitalstack.betfair.tech_academy_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping(value="/productsManager")
public class ProductsManagerController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getProducts(){

        return "products";
    }
}
