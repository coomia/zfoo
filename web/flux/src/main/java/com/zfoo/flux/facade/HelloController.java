package com.zfoo.flux.facade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    public HelloController() {
        System.out.println("HelloController................");
    }

    @Value("${name}")
    private String name;

    @GetMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("name", name);
        System.out.println(name);
        return "/index.html";
    }

    @RequestMapping("/index")
    public String index() {
        return "forward:/index.html";
    }

    /**
     * 404 error
     * @return
     */
    @RequestMapping("/404")
    public String error404() {
        return "/index.html";
    }

    /**
     * 500 error
     * @return
     */
    @RequestMapping("/500")
    public String error500() {
        return "/index.html";
    }

}
