package com.mt.microservice.microservices.controllers;

import com.mt.microservice.microservices.beans.HelloBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String getHello() {
        return "Hello World";
    }

    @GetMapping("/hello-bean")
    public HelloBean getBean() {
        return new HelloBean("This is Hello-Bean");
    }

    @GetMapping("/hello-bean/path-var/{name}")
    public HelloBean getBeanByName(@PathVariable String name) {
        return new HelloBean("This is Hello-Bean , " + name);
    }

}
