package com.base.framework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * @Description:
 * BasicSwaggerController
 * </pre>
 *
 * @version v1.0
 * @ClassName: BasicSwaggerController
 * @Author: sanwu
 * @Date: 2020/12/26 16:18
 */
@RestController
public class BasicSwaggerController {

    @GetMapping("/getSwagger")
    public String baseReturn(){
        return "123";
    }
}
