package com.sw.rpc.controller;

import com.sw.rpc.service.FetchServiceMateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config/syn")
public class SynController {

    @Autowired
    private FetchServiceMateDataService fetchServiceMateDataService;

    @GetMapping
    public void synRegisterConfig() {
        fetchServiceMateDataService.fetch();
    }
}
