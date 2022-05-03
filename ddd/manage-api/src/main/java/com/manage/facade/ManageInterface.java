package com.manage.facade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/third-party")
public interface ManageInterface {

    @GetMapping("/info")
    String getRequestInfo(@RequestParam String requestId);
}
