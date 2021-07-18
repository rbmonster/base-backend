package com.sanwu.once.controller;

import com.sanwu.once.security.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: UserLoginConterller
 * @Author: sanwu
 * @Date: 2020/12/29 15:49
 */

@RequestMapping("/api/v1/admin/login")
@RestController
public class UserLoginController {

    @Autowired
    TokenHelper tokenHelper;

    @GetMapping
    private String toLogin(HttpServletRequest request, String username, String password) {
        // 登录成功
        if (password.equals("112233")) {
            String token = tokenHelper.generateToken(username+"~role");
            // 登陆成功
            return token;
        }
        return "认证失败";
    }

}
