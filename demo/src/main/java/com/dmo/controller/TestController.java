package com.dmo.controller;

import com.dmo.utils.AttaIdUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
    public String getCode() {
        return AttaIdUtils.getByPrgetByProdCodeodCode("12312");
    }
}
