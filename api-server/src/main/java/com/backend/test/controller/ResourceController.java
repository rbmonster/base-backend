package com.backend.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: ResourceController
 * @Author: sanwu
 * @Date: 2021/7/9 1:00
 */
@RestController
@RequestMapping("/api/v1/resource")
public class ResourceController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String getResource() {
        return "hi this is resource";
    }
}
