package com.sw.service2.controller;

import com.sw.api.IHelloWorld;
import com.sw.api.model.Person;
import com.sw.rpc.annotation.SwRpcConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class TestController {

    @SwRpcConsumer(interfaceClass = IHelloWorld.class)
    private IHelloWorld helloWorld;

    @GetMapping()
    public String get() {
        Person name = helloWorld.findPersonByName("name");
        return name.getName();
    }

}
