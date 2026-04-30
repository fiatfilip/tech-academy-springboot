package ro.digitalstack.betfair.tech_academy_springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/hello")
public class HelloController {
    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public ModelAndView sayHello(@PathVariable String name,@RequestParam Boolean polite, ModelAndView modelAndView){
        modelAndView.setViewName("hello");
        if(polite){
            name = name + ", sir";
        } else {
            name = name + ", dude";
        }
        modelAndView.addObject("nameInTemplate", name);
        return modelAndView;
    }
}
