package com.demo.facade.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class DDDDemoController {

    @GetMapping("/demo")
    public String getDemo(){
        return "";
    }


    @GetMapping("/info")
    public String getManageProductInfo(@RequestParam String productId){
        return "";
    }

    @PostMapping("/demo")
    public boolean isProductAvailable(@RequestBody String productId){
        return true;
    }
}
