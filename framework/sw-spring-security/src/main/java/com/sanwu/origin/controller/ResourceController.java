package com.sanwu.origin.controller;

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
 * @Date: 2021/7/18 22:43
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping
    public String resource(){
        return "sdfasdf";
    }

    @GetMapping("/ad")
    public String adResource(){
        return "sdfasdf";
    }
}
