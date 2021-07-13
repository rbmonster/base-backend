package com.backend.test.controller;

import com.backend.test.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: LoginInController
 * @Author: sanwu
 * @Date: 2021/7/9 0:55
 */
@RestController
@RequestMapping("/api/v1/admin")
public class LoginInController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
//
//    @GetMapping("/login")
//    public String loginIn(@RequestParam("username") String username, @RequestParam("password") String password) {
//        return jwtTokenHelper.generateToken(username);
//    }
}
